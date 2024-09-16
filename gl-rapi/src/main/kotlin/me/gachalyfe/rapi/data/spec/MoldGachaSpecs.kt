package me.gachalyfe.rapi.data.spec

import me.gachalyfe.rapi.data.entity.MoldGachaEntity
import me.gachalyfe.rapi.domain.model.MoldType
import org.springframework.data.jpa.domain.Specification

object MoldGachaSpecs {
    fun hasType(moldType: MoldType) =
        Specification<MoldGachaEntity> { root, _, cb ->
            cb.equal(root.get<Int>("type"), moldType.ordinal)
        }
}
