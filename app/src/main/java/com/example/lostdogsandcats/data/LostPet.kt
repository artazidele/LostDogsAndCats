package com.example.lostdogsandcats.data

/**
 * @property petId
 * @property name
 * @property isDog
 * @property description
 * @property photo
 * @property date
 * @property place
 * @property number
 * @property userId
 */
data class LostPet(
    val petId: String,
    val name: String,
    val isDog: Boolean,
    var description: String,
    var photo: String,
    var date: String,
    var place: String,
    var number: String,
    var userId: String
)
