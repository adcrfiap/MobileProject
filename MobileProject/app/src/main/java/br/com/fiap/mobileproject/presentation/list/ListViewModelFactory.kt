package br.com.fiap.mobileproject.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mobileproject.domain.usecases.DeleteProductsUseCase
import br.com.fiap.mobileproject.domain.usecases.FindProductsUseCase
import br.com.fiap.mobileproject.domain.usecases.ListProductsUseCase
import br.com.fiap.mobileproject.domain.usecases.SignOutUseCase

class ListViewModelFactory(
    private  val listProductsUseCase: ListProductsUseCase,
    private  val deleteProductsUseCase: DeleteProductsUseCase,
    private  val findProductsUseCase: FindProductsUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            ListProductsUseCase::class.java,
            DeleteProductsUseCase::class.java,
            FindProductsUseCase::class.java
        ).newInstance(listProductsUseCase,deleteProductsUseCase,findProductsUseCase)
    }
}