package me.gachalyfe.rapi.data.repository

import me.gachalyfe.rapi.data.entity.AnomalyInterceptionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface AnomalyInterceptionRepository :
    JpaRepository<AnomalyInterceptionEntity, Long>,
    JpaSpecificationExecutor<AnomalyInterceptionEntity>
