package me.gachalyfe.rapi.data.repository

import me.gachalyfe.rapi.data.entity.SpecialInterceptionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SpecialInterceptionRepository : JpaRepository<SpecialInterceptionEntity, Long>
