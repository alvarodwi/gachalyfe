package me.gachalyfe.rapi.data.mapper

import me.gachalyfe.rapi.controller.dto.BannerGachaDTO
import me.gachalyfe.rapi.data.entity.BannerGachaEntity
import me.gachalyfe.rapi.domain.model.BannerGacha
import me.gachalyfe.rapi.domain.model.Nikke
import me.gachalyfe.rapi.utils.csv.schema.BannerGachaCsvSchema
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

fun BannerGacha.toCsvSchema() =
    BannerGachaCsvSchema(
        date = LocalDate.parse(date),
        pickUpName = pickUpName,
        gemsUsed = gemsUsed,
        ticketUsed = ticketUsed,
        totalAttempt = totalAttempt,
        totalSSR = totalSSR,
        nikkePulled = nikkePulled.map { it.name },
    )

fun BannerGachaCsvSchema.toModel(nikkePulledMapper: (List<String>) -> List<Nikke>) =
    BannerGacha(
        id = 0,
        date = date.format(DateTimeFormatter.ISO_DATE),
        pickUpName = pickUpName,
        gemsUsed = gemsUsed,
        ticketUsed = ticketUsed,
        totalAttempt = totalAttempt,
        totalSSR = totalSSR,
        nikkePulled = nikkePulledMapper(nikkePulled),
    )
