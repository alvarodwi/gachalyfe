package me.gachalyfe.rapi.controller.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import me.gachalyfe.rapi.controller.dto.equipment.ManufacturerEquipmentDTO

data class SpecialInterceptionDTO(
    val id: Long?,
    @field:NotBlank
    val date: String,
    @field:NotBlank
    val bossName: String,
    @field:Min(0) @field:Max(6)
    val t9Equipment: Int,
    @field:Min(0) @field:Max(6)
    val modules: Int,
    @field:Min(0) @field:Max(6)
    val t9ManufacturerEquipment: Int,
    @field:Min(0) @field:Max(6)
    val empty: Int,
    @field:Valid
    val equipments: List<ManufacturerEquipmentDTO>,
)
