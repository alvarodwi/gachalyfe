package me.gachalyfe.rapi.data.spec

import me.gachalyfe.rapi.data.entity.BannerGachaEntity
import org.springframework.data.jpa.domain.Specification

object BannerGachaSpecs {
    fun isSocialPulls() =
        Specification<BannerGachaEntity> { root, _, cb ->
            cb.equal(root.get<String>("pickUpName"), "Social")
        }

    fun isRegularPulls() =
        Specification<BannerGachaEntity> { root, _, cb ->
            cb.equal(root.get<String>("pickUpName"), "Regular")
        }

    fun isEventPulls() =
        Specification<BannerGachaEntity> { root, _, _ ->
            root.get<String>("pickUpName").`in`(listOf("Social", "Regular")).not()
        }
}
