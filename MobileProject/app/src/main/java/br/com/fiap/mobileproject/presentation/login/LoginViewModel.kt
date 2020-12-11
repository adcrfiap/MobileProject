package br.com.fiap.mobileproject.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.entity.User
import br.com.fiap.mobileproject.domain.entity.UserLogin
import br.com.fiap.mobileproject.domain.usecases.LoginUseCase
import br.com.fiap.mobileproject.domain.usecases.ResetPasswordUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private  val loginUseCase: LoginUseCase,
    private  val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    val loginState = MutableLiveData<RequestState<User>>()
    val resetPasswordState = MutableLiveData<RequestState<String>>()

    fun doLogin(email: String, senha: String){
        viewModelScope.launch {
            loginState.value = loginUseCase.doLogin(UserLogin(email, senha))
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            resetPasswordState.value =
                resetPasswordUseCase.resetPassword(email)
        }
    }

}