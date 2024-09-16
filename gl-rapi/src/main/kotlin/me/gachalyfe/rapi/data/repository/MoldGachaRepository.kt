package me.gachalyfe.rapi.data.repository

import me.gachalyfe.rapi.data.entity.MoldGachaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface MoldGachaRepository :
    JpaRepository<MoldGachaEntity, Long>,
    JpaSpecificationExecutor<MoldGachaEntity>
