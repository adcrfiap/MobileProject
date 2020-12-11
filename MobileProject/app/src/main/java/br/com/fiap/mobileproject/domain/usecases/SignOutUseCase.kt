package br.com.fiap.mobileproject.domain.usecases

import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.entity.User
import br.com.fiap.mobileproject.domain.entity.UserLogin
import br.com.fiap.mobileproject.domain.repository.UserRepository

class SignOutUseCase(
    private val userRepository: UserRepository
) {

    suspend fun signout(): RequestState<User> =
        userRepository.signout()

}