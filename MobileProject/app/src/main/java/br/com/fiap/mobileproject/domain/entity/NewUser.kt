package br.com.fiap.mobileproject.domain.entity

data class NewUser(
        val name: String,
        val email: String,
        val cnpj: String,
        val phone: String,
        val password: String
)