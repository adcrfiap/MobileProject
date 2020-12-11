package br.com.fiap.mobileproject.data.repository

import br.com.fiap.mobileproject.data.remote.datasource.ProductRemoteDataSource
import br.com.fiap.mobileproject.data.remote.datasource.UserRemoteDataSource
import br.com.fiap.mobileproject.domain.entity.*
import br.com.fiap.mobileproject.domain.repository.ProductRepository
import br.com.fiap.mobileproject.domain.repository.UserRepository

class ProductRepositoryImpl (
    val productRemoteDataSource: ProductRemoteDataSource
) : ProductRepository {

    override suspend fun listProducts(): RequestState<MutableList<Product>> {
        return  productRemoteDataSource.listProducts()
    }

    override suspend fun create(newProduct: NewProduct): RequestState<NewProduct> {
        return productRemoteDataSource.create(newProduct)
    }

    override suspend fun delete(id: String): RequestState<String> {
        return productRemoteDataSource.delete(id)
    }

    override suspend fun find(description: String): RequestState<MutableList<Product>> {
        return productRemoteDataSource.find(description)
    }

    override suspend fun update(): RequestState<String>{
        return productRemoteDataSource.update()
    }
}