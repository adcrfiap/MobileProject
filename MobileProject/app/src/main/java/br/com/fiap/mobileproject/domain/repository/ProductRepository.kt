package br.com.fiap.mobileproject.domain.repository

import br.com.fiap.mobileproject.domain.entity.NewProduct
import br.com.fiap.mobileproject.domain.entity.Product
import br.com.fiap.mobileproject.domain.entity.ProductUpdate
import br.com.fiap.mobileproject.domain.entity.RequestState

interface ProductRepository {
    suspend fun listProducts(): RequestState<MutableList<Product>>
    suspend fun create(newProduct: NewProduct): RequestState<NewProduct>
    suspend fun delete(id: String): RequestState<String>
    suspend fun find(description: String): RequestState<MutableList<Product>>
    suspend fun update(): RequestState<String>
}