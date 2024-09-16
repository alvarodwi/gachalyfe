package me.gachalyfe.rapi.data.spec

import me.gachalyfe.rapi.data.entity.ManufacturerEquipmentEntity
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import org.springframework.data.jpa.domain.Specification

object ManufacturerEquipmentSpecs {
    fun isInType(sourceType: EquipmentSourceType): Specification<ManufacturerEquipmentEntity> =
        Specification<ManufacturerEquipmentEntity> { root, cq, cb ->
            cb.equal(root.get<Int>("sourceType"), sourceType.ordinal)
        }

    fun withSourceId(sourceId: Long): Specification<ManufacturerEquipmentEntity> =
        Specification<ManufacturerEquipmentEntity> { root, cq, cb ->
            cb.equal(root.get<Long>("sourceId"), sourceId)
        }
}
