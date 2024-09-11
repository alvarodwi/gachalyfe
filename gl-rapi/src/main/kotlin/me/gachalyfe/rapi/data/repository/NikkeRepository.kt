package me.gachalyfe.rapi.data.repository

import me.gachalyfe.rapi.data.entity.NikkeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface NikkeRepository : JpaRepository<NikkeEntity, Long>
