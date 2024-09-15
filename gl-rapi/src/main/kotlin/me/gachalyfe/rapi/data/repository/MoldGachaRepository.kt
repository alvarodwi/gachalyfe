package me.gachalyfe.rapi.data.repository

import me.gachalyfe.rapi.data.entity.MoldGachaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MoldGachaRepository : JpaRepository<MoldGachaEntity, Long> {
    @Query("select m from mold_gacha m where type = :type")
    fun findAllByMoldType(
        @Param("type") type: Int,
    ): List<MoldGachaEntity>

    @Query("select m from mold_gacha m order by date desc limit :limit")
    fun findLatest(
        @Param("limit") limit: Int = 10,
    ): List<MoldGachaEntity>
}
