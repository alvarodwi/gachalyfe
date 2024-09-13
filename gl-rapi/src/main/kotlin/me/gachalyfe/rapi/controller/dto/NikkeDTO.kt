package me.gachalyfe.rapi.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class NikkeDTO(
    @field:Min(1)
    val id: Long,
    @field:NotBlank
    val name: String,
    val manufacturer: String?,
    @JsonProperty("class")
    val classType: String?,
    val burst: String?,
    val weapon: String?,
    val rarity: String?,
    val element: String?,
)
