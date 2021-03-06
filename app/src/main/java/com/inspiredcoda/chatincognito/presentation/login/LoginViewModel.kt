package com.inspiredcoda.chatincognito.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inspiredcoda.chatincognito.data.remote.NetworkEvent
import com.inspiredcoda.chatincognito.data.remote.dto.LoginRequest
import com.inspiredcoda.chatincognito.data.remote.dto.LoginResponse
import com.inspiredcoda.chatincognito.domain.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private var _loginState = MutableLiveData<UIState>()
    val loginState: LiveData<UIState>
        get() = _loginState

    private var _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                _loadingState.value = true
                val request = LoginRequest(username, password)
                when (val response = loginUseCase.login(request)) {
                    is NetworkEvent.Success<*> -> {
                        _loadingState.value = false
                        _loginState.value = UIState.Success(response.data as LoginResponse)
                    }
                    is NetworkEvent.Failure -> {
                        _loadingState.value = false
                        _loginState.value = UIState.Error(response.message)
                    }
                }
            } catch (ex: Exception) {
                _loadingState.value = false
                _loginState.value = UIState.Error(ex.message ?: "Error validating user")
            }
        }
    }

}