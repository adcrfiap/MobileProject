package br.com.fiap.mobileproject.data.repository

import br.com.fiap.mobileproject.data.remote.datasource.UserRemoteDataSource
import br.com.fiap.mobileproject.domain.entity.NewUser
import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.entity.User
import br.com.fiap.mobileproject.domain.entity.UserLogin
import br.com.fiap.mobileproject.domain.repository.UserRepository

class UserRepositoryImpl (
    val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {


    override suspend fun getUserLogged(): RequestState<User> {
        return userRemoteDataSource.getUserLogged()
    }

    override suspend fun doLogin(userLogin: UserLogin): RequestState<User> {
        return  userRemoteDataSource.doLogin(userLogin)
    }

    override suspend fun resetPassword(email: String): RequestState<String> {
        return userRemoteDataSource.resetPassword(email)
    }

    override suspend fun create(newUser: NewUser): RequestState<User> {
        return userRemoteDataSource.create(newUser)
    }

    override suspend fun signout(): RequestState<User> {
        return userRemoteDataSource.signout()
    }
}