package me.gachalyfe.rapi.data.repository.gacha

import me.gachalyfe.rapi.data.entity.gacha.MoldGachaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MoldGachaRepository : JpaRepository<MoldGachaEntity, Long>
