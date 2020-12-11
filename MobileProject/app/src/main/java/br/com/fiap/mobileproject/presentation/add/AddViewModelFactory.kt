package br.com.fiap.mobileproject.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mobileproject.domain.usecases.CreateProductsUseCase
import br.com.fiap.mobileproject.domain.usecases.ListProductsUseCase
import br.com.fiap.mobileproject.domain.usecases.SignOutUseCase

class AddViewModelFactory(
    private  val createProductsUseCase: CreateProductsUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            CreateProductsUseCase::class.java
        ).newInstance(createProductsUseCase)
    }
}