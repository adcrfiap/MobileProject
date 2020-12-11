package br.com.fiap.mobileproject.data.remote.mapper

import br.com.fiap.mobileproject.data.remote.model.NewUserFirebasePayload
import br.com.fiap.mobileproject.domain.entity.NewUser
import br.com.fiap.mobileproject.domain.entity.User

object NewUserFirebasePayloadMapper {
    fun mapToNewUser (newUserFirebasePayload: NewUserFirebasePayload) = NewUser(
        name = newUserFirebasePayload. name ?: "",
        email = newUserFirebasePayload. email ?: "",
        cnpj = newUserFirebasePayload. cnpj ?: "",
        phone = newUserFirebasePayload. phone ?: "",
        password = newUserFirebasePayload. password ?: ""
    )
    fun mapToNewUserFirebasePayload (newUser: NewUser ) = NewUserFirebasePayload(
        name = newUser. name,
        email = newUser. email,
        cnpj = newUser. cnpj,
        phone = newUser. phone,
        password = newUser. password
    )
    fun mapToUser (newUserFirebasePayload: NewUserFirebasePayload, userId: String) = User(
        name  = newUserFirebasePayload. name ?: "",
        email = newUserFirebasePayload. email ?: "",
        uid   = userId
    )
}