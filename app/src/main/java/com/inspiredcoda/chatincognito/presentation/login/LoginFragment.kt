package com.inspiredcoda.chatincognito.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.inspiredcoda.chatincognito.R
import com.inspiredcoda.chatincognito.data.remote.dto.LoginResponse
import com.inspiredcoda.chatincognito.databinding.FragmentLoginBinding
import com.inspiredcoda.chatincognito.domain.UIState
import com.inspiredcoda.chatincognito.utils.hideKeyboard
import com.inspiredcoda.chatincognito.utils.show
import com.inspiredcoda.chatincognito.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding!!

    val loginViewModel: LoginViewModel by viewModels()

    private val navController: NavController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()

        binding.loginBtn.setOnClickListener {
            if (validateInput()) {
                hideKeyboard()
                loginViewModel.login(
                    username = binding.loginUsername.editText?.text.toString(),
                    password = binding.loginPassword.editText?.text.toString()
                )
            }
        }

        binding.loginNewAccount.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    private fun observer() {
        loginViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.root.show(isLoading)
        }

        loginViewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Success<*> -> {
                    val loginResponse = state.data as LoginResponse
                    snackbar(loginResponse.username ?: "No name found attached to this user")
                }
                is UIState.Error -> {
                    snackbar(state.message)
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        if (binding.loginUsername.editText?.text.isNullOrBlank()) {
            binding.loginUsername.error = "Username is required"
            return false
        } else {
            binding.loginUsername.error = null
        }

        if (binding.loginPassword.editText?.text.isNullOrBlank()) {
            binding.loginPassword.error = "Password is required"
            return false
        } else {
            binding.loginPassword.error = null
        }
        return true
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}