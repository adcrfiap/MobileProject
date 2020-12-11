package br.com.fiap.mobileproject.domain.usecases

import br.com.fiap.mobileproject.domain.entity.*
import br.com.fiap.mobileproject.domain.repository.ProductRepository
import br.com.fiap.mobileproject.domain.repository.UserRepository

class UpdateProductsUseCase(
    private val productRepository: ProductRepository
){

    suspend fun update(): RequestState<String> = productRepository.update()

}