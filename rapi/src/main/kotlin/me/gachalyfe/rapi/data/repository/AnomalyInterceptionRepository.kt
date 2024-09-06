package me.gachalyfe.rapi.data.repository

import me.gachalyfe.rapi.data.entity.AnomalyInterceptionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AnomalyInterceptionRepository : JpaRepository<AnomalyInterceptionEntity, Long>
