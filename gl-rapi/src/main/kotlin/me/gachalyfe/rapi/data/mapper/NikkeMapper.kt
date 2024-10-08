package me.gachalyfe.rapi.data.mapper

import me.gachalyfe.rapi.controller.dto.NikkeDTO
import me.gachalyfe.rapi.data.entity.NikkeEntity
import me.gachalyfe.rapi.domain.model.Nikke

fun Nikke.toEntity() =
    NikkeEntity(
        id = id,
        name = name,
        manufacturer = manufacturer,
        classType = classType,
        burst = burst,
        weapon = weapon,
        element = element,
    )

fun NikkeEntity.toModel() =
    Nikke(
        id = id,
        name = name,
        manufacturer = manufacturer,
        classType = classType,
        burst = burst,
        weapon = weapon,
        element = element,
    )

fun NikkeDTO.toModel() =
    Nikke(
        id = id,
        name = name,
        manufacturer = manufacturer ?: "",
        classType = classType ?: "",
        burst = burst?.let { processBurstCode(it) } ?: "",
        weapon = weapon ?: "",
        element = element ?: "",
    )

private fun processBurstCode(burst: String): String = if (burst == "p") "1,2,3" else burst
