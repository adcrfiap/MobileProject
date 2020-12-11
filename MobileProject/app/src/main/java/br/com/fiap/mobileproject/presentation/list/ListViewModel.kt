package br.com.fiap.mobileproject.presentation.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mobileproject.domain.entity.Product
import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.entity.User
import br.com.fiap.mobileproject.domain.usecases.DeleteProductsUseCase
import br.com.fiap.mobileproject.domain.usecases.FindProductsUseCase
import br.com.fiap.mobileproject.domain.usecases.ListProductsUseCase
import br.com.fiap.mobileproject.domain.usecases.SignOutUseCase
import kotlinx.coroutines.launch

class ListViewModel(
    private  val listProductsUseCase: ListProductsUseCase,
    private  val deleteProductsUseCase: DeleteProductsUseCase,
    private  val findProductsUseCase: FindProductsUseCase
) : ViewModel() {

    val ListProductsState = MutableLiveData<RequestState<MutableList<Product>>>()
    val FindProductsState = MutableLiveData<RequestState<MutableList<Product>>>()
    val deleteProductsState = MutableLiveData<RequestState<String>>()

    fun listProducts(){
        viewModelScope.launch {
            ListProductsState.value = listProductsUseCase.listProducts()
        }
    }

    fun delete(id: String){
        viewModelScope.launch {
            deleteProductsState.value = deleteProductsUseCase.delete(id)
        }
    }

    fun findProduct(description: String){
        viewModelScope.launch {
            FindProductsState.value = findProductsUseCase.findProducts(description)
        }
    }

}