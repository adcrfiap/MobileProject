package br.com.fiap.mobileproject.presentation.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mobileproject.domain.entity.NewUser
import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.entity.User
import br.com.fiap.mobileproject.domain.usecases.CreateUserUseCase
import kotlinx.coroutines.launch

class SignUPViewModel (
    private val createUserUseCase : CreateUserUseCase
) : ViewModel() {
    val newUserState = MutableLiveData<RequestState<User>>()
    fun create(name: String, email: String, cnpj: String, phone: String, password:
    String) {
        viewModelScope.launch {
            newUserState .value = createUserUseCase .create(
                NewUser(
                    name,
                    email,
                    cnpj,
                    phone,
                    password
                )
            )
        }
    }
}