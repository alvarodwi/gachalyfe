package me.gachalyfe.rapi.data.spec

import me.gachalyfe.rapi.data.entity.AnomalyInterceptionEntity
import org.springframework.data.jpa.domain.Specification

object AnomalyInterceptionSpecs {
    fun hasDate(date: String): Specification<AnomalyInterceptionEntity> =
        Specification<AnomalyInterceptionEntity> { root, _, cb ->
            cb.equal(root.get<String>("date"), date)
        }

    fun hasEquipmentDropped(): Specification<AnomalyInterceptionEntity> =
        Specification<AnomalyInterceptionEntity> { root, _, cb ->
            cb.and(
                cb.isTrue(root.get("dropped")),
                cb.notEqual(root.get<String>("dropType"), "Modules"),
            )
        }
}
