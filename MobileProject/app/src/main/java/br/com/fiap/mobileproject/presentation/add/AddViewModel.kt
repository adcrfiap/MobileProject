package br.com.fiap.mobileproject.presentation.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mobileproject.domain.entity.NewProduct
import br.com.fiap.mobileproject.domain.entity.Product
import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.usecases.CreateProductsUseCase
import br.com.fiap.mobileproject.domain.usecases.ListProductsUseCase
import kotlinx.coroutines.launch

class AddViewModel(
    private  val createProductsUseCase: CreateProductsUseCase
) : ViewModel() {

    val createProductsState = MutableLiveData<RequestState<NewProduct>>()

    fun create(description: String, quantity: Number,value: Number){
        viewModelScope.launch {
            createProductsState.value = createProductsUseCase
                .create(NewProduct(description,quantity,"",value))
        }
    }

}