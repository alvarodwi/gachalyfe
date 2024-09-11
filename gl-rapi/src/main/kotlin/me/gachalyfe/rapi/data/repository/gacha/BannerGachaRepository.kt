package me.gachalyfe.rapi.data.repository.gacha

import me.gachalyfe.rapi.data.entity.gacha.BannerGachaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface BannerGachaRepository : JpaRepository<BannerGachaEntity, Long> {
    @Query("select b from banner_gacha b where pickUpName = 'Social' order by date desc")
    fun findAllByPickUpNameSocial(): List<BannerGachaEntity>

    @Query("select b from banner_gacha b where pickUpName = 'Regular' order by date desc")
    fun findAllByPickUpNameRegular(): List<BannerGachaEntity>

    @Query("select b from banner_gacha b where pickUpName not in ('Social', 'Regular', 'Unknown') order by date desc")
    fun findAllByPickUpNameLimited(): List<BannerGachaEntity>

    @Query("select b from banner_gacha b where pickUpName = :name")
    fun findAllByPickUpName(
        @Param("name") name: String,
    ): List<BannerGachaEntity>
}
