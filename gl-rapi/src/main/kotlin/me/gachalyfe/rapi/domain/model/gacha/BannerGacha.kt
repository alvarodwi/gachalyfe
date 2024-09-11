package me.gachalyfe.rapi.domain.model.gacha

import me.gachalyfe.rapi.domain.model.Nikke

data class BannerGacha(
    val id: Long,
    val date: String,
    val pickUpName: String,
    val gemsUsed: Int,
    val ticketUsed: Int,
    val totalAttempt: Int,
    val totalSSR: Int,
    val nikkePulled: List<Nikke> = emptyList(),
)
