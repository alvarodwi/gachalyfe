package me.gachalyfe.rapi.data.repository.gacha

import me.gachalyfe.rapi.data.entity.gacha.BannerGachaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BannerGachaRepository : JpaRepository<BannerGachaEntity, Long>
