package com.example.lostdogsandcats

data class Pet(
    //val petID: String = "",
    val name: String = "",
    val isDog: Boolean = false, //String = "",
    var description: String = "",
    var photo: String = "",
    var date: String = "",
    var place: String = "",
    var number: String = "",
    var userId: String = ""
)
data class LostPet(
    val petId: String = "",
    val name: String = "",
    val dog: String = "",
    var description: String = "",
    var photo: String = "",
    var date: String = "",
    var place: String = "",
    var number: String = "",
    var userId: String = ""
)
