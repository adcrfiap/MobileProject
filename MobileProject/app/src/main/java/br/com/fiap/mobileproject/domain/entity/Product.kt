package br.com.fiap.mobileproject.domain.entity

import java.lang.reflect.Constructor

data class Product(
    val description: String,
    val quantity: Number,
    val userid: String,
    val value: Number,
    val id: String
){
    constructor(): this("",0,"",0, "")
}