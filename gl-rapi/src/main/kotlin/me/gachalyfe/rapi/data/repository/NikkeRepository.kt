package me.gachalyfe.rapi.data.repository

import me.gachalyfe.rapi.data.entity.NikkeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface NikkeRepository :
    JpaRepository<NikkeEntity, Long>,
    JpaSpecificationExecutor<NikkeEntity>
