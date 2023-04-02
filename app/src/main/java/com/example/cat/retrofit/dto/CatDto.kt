package com.example.cat.retrofit.dto

import com.example.cat.usecase.model.Cat

data class CatDto(
    val id: String,
    val url: String,
    val width: String,
    val height: String,
)

fun CatDto.toCat() = Cat(this.url)

