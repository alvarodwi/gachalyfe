package me.gachalyfe.rapi.domain.model.stats

data class BannerGachaStats(
    val category: String,
    val totalGemsUsed: Int,
    val totalTicketUsed: Int,
    val totalAttempts: Int,
    val totalSSR: Int,
)
