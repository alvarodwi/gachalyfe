package me.gachalyfe.rapi.domain.model.stats

import me.gachalyfe.rapi.domain.model.MoldType

data class MoldGachaStats(
    val moldType: MoldType,
    val amount: Int,
    val totalSSR: Int,
)
