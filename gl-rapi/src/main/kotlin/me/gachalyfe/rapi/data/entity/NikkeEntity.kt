package me.gachalyfe.rapi.data.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "nikke")
data class NikkeEntity(
    @Id
    @Column(name = "id", columnDefinition = "INTEGER")
    val id: Long,
    @Column(unique = true)
    val name: String,
    val manufacturer: String,
    val classType: String,
    val burst: String,
    val weapon: String,
    val element: String,
)
