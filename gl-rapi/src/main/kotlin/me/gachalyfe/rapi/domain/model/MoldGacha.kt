package me.gachalyfe.rapi.domain.model

data class MoldGacha(
    val id: Long = 0,
    val date: String,
    val type: MoldType,
    val amount: Int,
    val totalSSR: Int,
    val nikkePulled: List<Nikke> = emptyList(),
)
