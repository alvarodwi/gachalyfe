package me.gachalyfe.rapi.data.mapper

import me.gachalyfe.rapi.controller.dto.AnomalyInterceptionDTO
import me.gachalyfe.rapi.data.entity.AnomalyInterceptionEntity
import me.gachalyfe.rapi.domain.model.AnomalyInterception
import me.gachalyfe.rapi.service.csv.schema.AnomalyInterceptionCsvSchema
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun AnomalyInterceptionEntity.toModel() =
    AnomalyInterception(
        id = id,
        date = date,
        bossName = bossName,
        stage = stage,
        dropType = dropType,
        dropped = dropped,
        modules = modules,
    )

fun AnomalyInterceptionDTO.toModel() =
    AnomalyInterception(
        id = id ?: 0,
        date = date,
        bossName = bossName,
        stage = stage,
        dropType = dropType,
        dropped = dropped,
        modules = modules,
        equipment = equipment?.toModel(),
    )

fun AnomalyInterception.toEntity() =
    AnomalyInterceptionEntity(
        id = id,
        date = date,
        bossName = bossName,
        stage = stage,
        dropType = dropType,
        dropped = dropped,
        modules = modules,
    )

fun AnomalyInterception.toCsvSchema() =
    AnomalyInterceptionCsvSchema(
        date = LocalDate.parse(date),
        bossName = bossName,
        stage = stage,
        dropType = dropType,
        dropped = dropped,
        modules = modules,
    )

fun AnomalyInterceptionCsvSchema.toModel() =
    AnomalyInterception(
        id = 0,
        date = date.format(DateTimeFormatter.ISO_DATE),
        bossName = bossName,
        stage = stage,
        dropType = dropType,
        dropped = dropped,
        modules = modules,
    )
