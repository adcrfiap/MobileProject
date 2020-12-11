package br.com.fiap.mobileproject.domain.usecases

import br.com.fiap.mobileproject.domain.entity.RequestState
import br.com.fiap.mobileproject.domain.repository.UserRepository

class ResetPasswordUseCase(
    private val userRepository: UserRepository
) {
    suspend fun resetPassword(email: String): RequestState<String> =
        userRepository.resetPassword(email)
}