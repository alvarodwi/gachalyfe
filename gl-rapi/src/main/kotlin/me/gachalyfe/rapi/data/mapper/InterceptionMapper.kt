package me.gachalyfe.rapi.data.mapper

import me.gachalyfe.rapi.controller.dto.AnomalyInterceptionDTO
import me.gachalyfe.rapi.controller.dto.SpecialInterceptionDTO
import me.gachalyfe.rapi.data.entity.AnomalyInterceptionEntity
import me.gachalyfe.rapi.data.entity.SpecialInterceptionEntity
import me.gachalyfe.rapi.domain.model.AnomalyInterception
import me.gachalyfe.rapi.domain.model.SpecialInterception
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface InterceptionMapper {
    fun toModel(dto: AnomalyInterceptionDTO): AnomalyInterception

    fun toEntity(model: AnomalyInterception): AnomalyInterceptionEntity

    @Mapping(target = "equipment", ignore = true)
    fun toModel(entity: AnomalyInterceptionEntity): AnomalyInterception

    fun toModel(dto: SpecialInterceptionDTO): SpecialInterception

    fun toEntity(model: SpecialInterception): SpecialInterceptionEntity

    @Mapping(target = "equipments", expression = "java(java.util.Collections.emptyList())")
    fun toModel(entity: SpecialInterceptionEntity): SpecialInterception
}
