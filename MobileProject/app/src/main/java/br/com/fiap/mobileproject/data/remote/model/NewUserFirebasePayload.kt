package br.com.fiap.mobileproject.data.remote.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class NewUserFirebasePayload (

    val name: String? = null,
    val email: String? = null,
    val cnpj: String? = null,
    val phone: String? = null,
    @get:Exclude val password: String? = null

)
