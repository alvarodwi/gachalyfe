package me.gachalyfe.rapi.controller.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class BannerGachaDTO(
    val id: Long?,
    @field:NotBlank
    val date: String,
    @field:NotBlank
    val pickUpName: String,
    @field:Min(0)
    val gemsUsed: Int,
    @field:Min(0)
    val ticketUsed: Int,
    @field:Min(0)
    val totalAttempt: Int,
    @field:Min(0)
    val totalSSR: Int,
    @field:Valid
    val nikkePulled: List<NikkeDTO> = emptyList(),
)
