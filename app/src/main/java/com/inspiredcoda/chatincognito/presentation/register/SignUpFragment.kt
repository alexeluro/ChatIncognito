package com.inspiredcoda.chatincognito.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.inspiredcoda.chatincognito.R
import com.inspiredcoda.chatincognito.data.remote.dto.BaseResponse
import com.inspiredcoda.chatincognito.data.remote.dto.SignUpResponse
import com.inspiredcoda.chatincognito.databinding.FragmentSignUpBinding
import com.inspiredcoda.chatincognito.domain.UIState
import com.inspiredcoda.chatincognito.utils.dateDialog
import com.inspiredcoda.chatincognito.utils.hideKeyboard
import com.inspiredcoda.chatincognito.utils.show
import com.inspiredcoda.chatincognito.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding!!

    private val signUpViewModel: SignUpViewModel by viewModels()

    private val navController: NavController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            toolbar.backBtn.also {
                it.setOnClickListener {
                    navController.popBackStack()
                }
                it.setImageResource(R.drawable.ic_close)
            }
            toolbar.toolbarTitle.text = "Register"

            binding.registerBtn.setOnClickListener {
                if (validateInputs()) {
                    hideKeyboard()
                    signUpViewModel.registerUser(
                        username = regUsername.editText?.text.toString(),
                        phoneNumber = regPhone.editText?.text.toString(),
                        email = regEmail.editText?.text.toString(),
                        dateOfBirth = regDob.editText?.text.toString(),
                        profilePic = "",
                        password = regPassword.editText?.text.toString()
                    )
                }
            }

            binding.regDob.editText?.setOnClickListener {
                dateDialog { date ->
                    binding.regDob.editText?.setText(date)
                }
            }
        }

        observer()
    }

    private fun observer() {
        signUpViewModel.loadingState.observe(viewLifecycleOwner) { state ->
            binding.progressBar.root.show(state)
        }

        signUpViewModel.signUpState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Success<*> -> {
                    val resp = state.data as BaseResponse<SignUpResponse>
                    snackbar(resp.data?.username ?: "Username gotten")
                    navController.popBackStack()
                }
                is UIState.Error -> {
                    snackbar(state.message)
                }
            }

        }
    }

    private fun validateInputs(): Boolean {
        if (binding.regUsername.editText?.text.isNullOrBlank()) {
            binding.regUsername.error = "Username is required"
            return false
        } else {
            binding.regUsername.error = null
        }

        if (binding.regEmail.editText?.text.isNullOrBlank()) {
            binding.regEmail.error = "Email is required"
            return false
        } else {
            binding.regEmail.error = null
        }

        if (binding.regPhone.editText?.text.isNullOrBlank()) {
            binding.regPhone.error = "Phone Number is required"
            return false
        } else {
            binding.regPhone.error = null
        }

        if (binding.regDob.editText?.text.isNullOrBlank()) {
            binding.regDob.error = "Date of Birth is required"
            return false
        } else {
            binding.regDob.error = null
        }

        if (binding.regPassword.editText?.text.isNullOrBlank()) {
            binding.regPassword.error = "Password is required"
            return false
        } else {
            binding.regPassword.error = null
        }

        if (binding.regConfirmPassword.editText?.text.isNullOrBlank()) {
            binding.regConfirmPassword.error = "Password Confirmation is required"
            return false
        } else {
            binding.regConfirmPassword.error = null
        }

        if (binding.regPassword.editText?.text.toString() != binding.regConfirmPassword.editText?.text.toString()) {
            binding.regConfirmPassword.error = "Passwords don't match"
            return false
        } else {
            binding.regConfirmPassword.error = null
        }

        return true
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}