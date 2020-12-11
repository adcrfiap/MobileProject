package br.com.fiap.mobileproject.domain.entity

import br.com.concrete.canarinho.validator.ValidadorCNPJ

data class User (
        val name: String,
        val email: String,
        val uid: String
)