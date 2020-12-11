package br.com.fiap.mobileproject.domain.usecases

import br.com.fiap.mobileproject.domain.entity.NewProduct
import br.com.fiap.mobileproject.domain.entity.Product
import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.entity.User
import br.com.fiap.mobileproject.domain.repository.ProductRepository
import br.com.fiap.mobileproject.domain.repository.UserRepository

class DeleteProductsUseCase(
    private val productRepository: ProductRepository
){

    suspend fun delete(id: String) :RequestState<String> = productRepository.delete(id)

}