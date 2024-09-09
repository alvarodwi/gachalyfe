package me.gachalyfe.rapi.data.repository

import me.gachalyfe.rapi.data.entity.SpecialInterceptionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface SpecialInterceptionRepository : JpaRepository<SpecialInterceptionEntity, Long> {
    @Query("select s from special_interceptions s order by s.date desc limit 10")
    fun findLast10(): List<SpecialInterceptionEntity>

    @Query("select s from special_interceptions s where date = :date and t9ManufacturerEquipment > 0")
    fun findByDateAndEquipmentDrops(@Param("date") date: String) : Optional<SpecialInterceptionEntity>
}
