package me.gachalyfe.rapi.data.mapper

import me.gachalyfe.rapi.controller.dto.MoldGachaDTO
import me.gachalyfe.rapi.data.entity.MoldGachaEntity
import me.gachalyfe.rapi.domain.model.MoldGacha
import me.gachalyfe.rapi.domain.model.MoldType
import me.gachalyfe.rapi.domain.model.Nikke
import me.gachalyfe.rapi.utils.csv.schema.MoldGachaCsvSchema
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

fun MoldGacha.toCsvSchema() =
    MoldGachaCsvSchema(
        date = LocalDate.parse(date),
        type = type.name,
        amount = amount,
        totalSSR = totalSSR,
        nikkePulled = nikkePulled.map { it.name },
    )

fun MoldGachaCsvSchema.toModel(nikkePulledMapper: (List<String>) -> List<Nikke>) =
    MoldGacha(
        id = 0,
        date = date.format(DateTimeFormatter.ISO_DATE),
        type = MoldType.valueOf(type.uppercase()),
        amount = amount,
        totalSSR = totalSSR,
        nikkePulled = nikkePulledMapper(nikkePulled),
    )
