package br.com.fiap.mobileproject.domain.usecases

import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.entity.User
import br.com.fiap.mobileproject.domain.repository.UserRepository

class GetUserLoggedUseCase(
        private val userRepository: UserRepository
){

    suspend fun getUserLogged() : RequestState<User> = userRepository.getUserLogged()

}