package me.gachalyfe.rapi.data.repository

import me.gachalyfe.rapi.data.entity.AnomalyInterceptionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AnomalyInterceptionRepository : JpaRepository<AnomalyInterceptionEntity, Long> {
    @Query("select a from anomaly_interceptions a order by a.date desc limit 10")
    fun findLast10(): List<AnomalyInterceptionEntity>
}
