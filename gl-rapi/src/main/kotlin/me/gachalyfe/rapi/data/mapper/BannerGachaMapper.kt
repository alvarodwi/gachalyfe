package me.gachalyfe.rapi.data.mapper

import me.gachalyfe.rapi.controller.dto.gacha.BannerGachaDTO
import me.gachalyfe.rapi.data.entity.gacha.BannerGachaEntity
import me.gachalyfe.rapi.domain.model.gacha.BannerGacha

fun BannerGacha.toEntity() =
    BannerGachaEntity(
        id = id,
        date = date,
        pickUpName = pickUpName,
        gemsUsed = gemsUsed,
        ticketUsed = ticketUsed,
        totalAttempt = totalAttempt,
        totalSSR = totalSSR,
        nikkePulled = nikkePulled.map { it.toEntity() },
    )

fun BannerGachaEntity.toModel() =
    BannerGacha(
        id = id,
        date = date,
        pickUpName = pickUpName,
        gemsUsed = gemsUsed,
        ticketUsed = ticketUsed,
        totalAttempt = totalAttempt,
        totalSSR = totalSSR,
        nikkePulled = nikkePulled.map { it.toModel() },
    )

fun BannerGachaDTO.toModel() =
    BannerGacha(
        id = id ?: 0,
        date = date,
        pickUpName = pickUpName,
        gemsUsed = gemsUsed,
        ticketUsed = ticketUsed,
        totalAttempt = totalAttempt,
        totalSSR = totalSSR,
        nikkePulled = nikkePulled.map { it.toModel() },
    )
