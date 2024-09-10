package me.gachalyfe.rapi.data.mapper

import me.gachalyfe.rapi.controller.dto.ManufacturerEquipmentDTO
import me.gachalyfe.rapi.data.entity.ManufacturerEquipmentEntity
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface EquipmentMapper {
    fun toModel(dto: ManufacturerEquipmentDTO): ManufacturerEquipment

    fun toEntity(model: ManufacturerEquipment): ManufacturerEquipmentEntity

    fun toModel(entity: ManufacturerEquipmentEntity): ManufacturerEquipment
}
