package br.com.fiap.mobileproject.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mobileproject.domain.usecases.LoginUseCase
import br.com.fiap.mobileproject.domain.usecases.ResetPasswordUseCase
import br.com.fiap.mobileproject.domain.usecases.SignOutUseCase

class HomeViewModelFactory(
    private  val signOutUseCase: SignOutUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            SignOutUseCase::class.java
        ).newInstance(signOutUseCase)
    }
}