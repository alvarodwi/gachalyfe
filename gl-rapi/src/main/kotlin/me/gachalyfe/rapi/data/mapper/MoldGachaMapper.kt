package me.gachalyfe.rapi.data.mapper

import me.gachalyfe.rapi.controller.dto.gacha.MoldGachaDTO
import me.gachalyfe.rapi.data.entity.gacha.MoldGachaEntity
import me.gachalyfe.rapi.domain.model.gacha.MoldGacha
import me.gachalyfe.rapi.domain.model.gacha.MoldType

fun MoldGacha.toEntity() =
    MoldGachaEntity(
        id = id,
        date = date,
        type = type.ordinal,
        amount = amount,
        totalSSR = totalSSR,
        nikkePulled = nikkePulled.map { it.toEntity() },
    )

fun MoldGachaEntity.toModel() =
    MoldGacha(
        id = id,
        date = date,
        type = MoldType.entries[type],
        amount = amount,
        totalSSR = totalSSR,
        nikkePulled = nikkePulled.map { it.toModel() },
    )

fun MoldGachaDTO.toModel() =
    MoldGacha(
        id = id ?: 0,
        date = date,
        type = MoldType.valueOf(type.uppercase()),
        amount = amount,
        totalSSR = totalSSR,
        nikkePulled = nikkePulled.map { it.toModel() },
    )
