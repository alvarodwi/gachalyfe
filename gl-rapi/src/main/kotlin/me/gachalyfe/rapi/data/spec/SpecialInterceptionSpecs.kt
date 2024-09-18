package me.gachalyfe.rapi.data.spec

import me.gachalyfe.rapi.data.entity.SpecialInterceptionEntity
import org.springframework.data.jpa.domain.Specification

object SpecialInterceptionSpecs {
    fun hasDate(date: String): Specification<SpecialInterceptionEntity> =
        Specification<SpecialInterceptionEntity> { root, _, cb ->
            cb.equal(root.get<String>("date"), date)
        }

    fun hasManufacturerEquipments() =
        Specification<SpecialInterceptionEntity> { root, _, cb ->
            cb.greaterThan(root.get("t9ManufacturerEquipment"), 0)
        }

    fun hasBossName(bossName: String) =
        Specification<SpecialInterceptionEntity> { root, _, cb ->
            cb.equal(root.get<String>("bossName"), bossName)
        }
}
