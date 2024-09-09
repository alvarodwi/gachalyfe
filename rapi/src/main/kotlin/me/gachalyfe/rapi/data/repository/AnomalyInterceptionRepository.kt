package me.gachalyfe.rapi.data.repository

import me.gachalyfe.rapi.data.entity.AnomalyInterceptionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AnomalyInterceptionRepository : JpaRepository<AnomalyInterceptionEntity, Long> {
    @Query("select a from anomaly_interceptions a order by a.date desc limit 10")
    fun findLast10(): List<AnomalyInterceptionEntity>

    @Query("select a.id from anomaly_interceptions a where date = :date and dropped = true and dropType != 'Modules'")
    fun findIdsByDateAndEquipmentDrops(@Param("date") date: String): List<Long>
    fun findAllByOrderByDateAsc(): List<AnomalyInterceptionEntity>
}
