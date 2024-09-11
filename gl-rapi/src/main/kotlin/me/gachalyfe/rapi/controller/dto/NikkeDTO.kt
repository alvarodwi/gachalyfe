package me.gachalyfe.rapi.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class NikkeDTO(
    @field:Min(1)
    val id: Long,
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val manufacturer: String,
    @field:NotBlank
    @JsonProperty("class")
    val classType: String,
    @field:NotBlank
    val burst: String,
    @field:NotBlank
    val weapon: String,
    @field:NotBlank
    val element: String,
)
