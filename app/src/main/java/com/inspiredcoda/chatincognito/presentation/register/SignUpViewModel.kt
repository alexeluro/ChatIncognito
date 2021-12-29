package com.inspiredcoda.chatincognito.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inspiredcoda.chatincognito.data.remote.NetworkEvent
import com.inspiredcoda.chatincognito.data.remote.dto.BaseResponse
import com.inspiredcoda.chatincognito.data.remote.dto.SignUpRequest
import com.inspiredcoda.chatincognito.data.remote.dto.SignUpResponse
import com.inspiredcoda.chatincognito.domain.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private var _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    private var _signUpState = MutableLiveData<UIState>()
    val signUpState: LiveData<UIState>
        get() = _signUpState

    fun registerUser(
        username: String,
        phoneNumber: String,
        email: String,
        dateOfBirth: String,
        profilePic: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                _loadingState.value = true
                val request = SignUpRequest(
                    username = username,
                    password = password,
                    phoneNumber = phoneNumber,
                    email = email,
                    profilePicture = profilePic,
                    dateOfBirth = dateOfBirth
                )
                when(val resp = signUpUseCase.signUp(request)){
                    is NetworkEvent.Success<*> -> {
                        _loadingState.value = false
                        _signUpState.value = UIState.Success(resp.data)
                    }
                    is NetworkEvent.Failure -> {
                        _loadingState.value = false
                        _signUpState.value = UIState.Error(resp.message)
                    }
                }
            }catch (ex: Exception){
                _loadingState.value = false
            }
        }
    }

}