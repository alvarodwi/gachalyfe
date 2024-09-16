package me.gachalyfe.rapi.data.spec

import me.gachalyfe.rapi.data.entity.NikkeEntity
import org.springframework.data.jpa.domain.Specification

object NikkeSpecs {
    fun hasName(name: String) =
        Specification<NikkeEntity> { root, _, cb ->
            cb.equal(root.get<String>("name"), name)
        }

    fun nameContains(query: String) =
        Specification<NikkeEntity> { root, _, cb ->
            cb.like(root.get("name"), "%$query%")
        }

    fun idIsIn(ids: List<Long>) =
        Specification<NikkeEntity> { root, _, cb ->
            root.get<Long>("id").`in`(ids)
        }
}
