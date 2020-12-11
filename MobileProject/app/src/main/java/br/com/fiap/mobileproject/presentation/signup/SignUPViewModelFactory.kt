package br.com.fiap.mobileproject.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mobileproject.domain.usecases.CreateUserUseCase

class SignUPViewModelFactory(
    private val createUserUseCase: CreateUserUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CreateUserUseCase::class.java).newInstance(createUserUseCase)
    }
}