package br.com.fiap.mobileproject.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.entity.User
import br.com.fiap.mobileproject.domain.usecases.SignOutUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private  val signOutUseCase: SignOutUseCase
) : ViewModel() {

    val signOutState = MutableLiveData<RequestState<User>>()

    fun doSignOut(){
        viewModelScope.launch {
            signOutState.value = signOutUseCase.signout()
        }
    }

}