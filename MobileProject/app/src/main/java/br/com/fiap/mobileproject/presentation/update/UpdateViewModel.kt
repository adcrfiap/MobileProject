package br.com.fiap.mobileproject.presentation.update

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.mobileproject.domain.entity.NewProduct
import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.usecases.CreateProductsUseCase
import br.com.fiap.mobileproject.domain.usecases.UpdateProductsUseCase
import kotlinx.coroutines.launch

class UpdateViewModel(
        private  val updateProductsUseCase: UpdateProductsUseCase
) : ViewModel() {

    val updateProductsState = MutableLiveData<RequestState<String>>()

    fun update(){
        viewModelScope.launch {
            updateProductsState.value = updateProductsUseCase.update()
        }
    }

}