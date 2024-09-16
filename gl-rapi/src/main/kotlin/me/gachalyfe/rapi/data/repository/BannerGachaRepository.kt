package me.gachalyfe.rapi.data.repository

import me.gachalyfe.rapi.data.entity.BannerGachaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface BannerGachaRepository :
    JpaRepository<BannerGachaEntity, Long>,
    JpaSpecificationExecutor<BannerGachaEntity>
