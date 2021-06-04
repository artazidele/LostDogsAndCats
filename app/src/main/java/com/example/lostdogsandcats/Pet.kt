package com.example.lostdogsandcats

data class Pet(
    val name: String = "",
    val isDog: Boolean = false,
    var description: String = "",
    var photo: String = "",
    var date: String = "",
    var place: String = "",
    var number: String = "",
    var userId: String = "0"
)
