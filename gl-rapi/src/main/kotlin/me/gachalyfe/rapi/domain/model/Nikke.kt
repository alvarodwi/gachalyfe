package me.gachalyfe.rapi.domain.model

data class Nikke(
    val id: Long = 0,
    val name: String,
    val manufacturer: String,
    val classType: String,
    val burst: String,
    val weapon: String,
    val element: String,
)
