package me.gachalyfe.rapi.controller.dto.gacha

import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import me.gachalyfe.rapi.controller.dto.NikkeDTO

data class MoldGachaDTO(
    val id: Long?,
    @field:NotBlank
    val type: String,
    @field:Min(0)
    val amount: Int,
    @field:Min(0)
    val totalSSR: Int,
    @field:Valid
    val nikkePulled: List<NikkeDTO> = emptyList(),
)
