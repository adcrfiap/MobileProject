package br.com.fiap.mobileproject.presentation.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mobileproject.domain.usecases.UpdateProductsUseCase

class UpdateViewModelFactory(
        private  val updateProductsUseCase: UpdateProductsUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
                UpdateProductsUseCase::class.java
        ).newInstance(updateProductsUseCase)
    }

}