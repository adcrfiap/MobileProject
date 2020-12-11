package br.com.fiap.mobileproject.domain.usecases

import br.com.fiap.mobileproject.domain.entity.NewUser
import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.entity.User
import br.com.fiap.mobileproject.domain.repository.UserRepository

class CreateUserUseCase(
    private val userRepository: UserRepository
) {
    suspend fun create(newUser: NewUser): RequestState<User> =
        userRepository.create(newUser)
}