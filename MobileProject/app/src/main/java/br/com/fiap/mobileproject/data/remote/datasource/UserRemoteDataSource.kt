package br.com.fiap.mobileproject.data.remote.datasource

import br.com.fiap.mobileproject.domain.entity.NewUser
import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.entity.User
import br.com.fiap.mobileproject.domain.entity.UserLogin

interface UserRemoteDataSource {

    suspend fun getUserLogged(): RequestState<User>

    suspend fun doLogin(userLogin: UserLogin): RequestState<User>

    suspend fun resetPassword(email: String): RequestState<String>

    suspend fun create(newUser: NewUser): RequestState<User>

    suspend fun signout(): RequestState<User>


}