package br.com.fiap.mobileproject.domain.entity

import java.lang.reflect.Constructor

data class NewProduct(
    val description: String,
    val quantity: Number,
    var userid: String,
    val value: Number
)