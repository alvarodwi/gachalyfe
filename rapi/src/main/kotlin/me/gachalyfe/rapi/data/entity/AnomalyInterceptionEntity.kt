package me.gachalyfe.rapi.data.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(
    name = "anomaly_interceptions",
)
data class AnomalyInterceptionEntity(
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY,
    )
    val id: Long = 0,
    val date: String,
    val bossName: String,
    val stage: Int,
    val dropType: String,
    val dropped: Boolean,
    val modules: Int,
)
