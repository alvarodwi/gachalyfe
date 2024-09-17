package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.domain.model.MoldGacha
import me.gachalyfe.rapi.domain.model.stats.MoldGachaStats
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface MoldGachaService {
    fun findAll(pageable: Pageable): Page<MoldGacha>

    fun findAll(sort: Sort): List<MoldGacha>

    fun findById(id: Long): MoldGacha

    fun save(model: MoldGacha): MoldGacha

    fun update(
        id: Long,
        model: MoldGacha,
    ): MoldGacha

    fun saveAll(data: List<MoldGacha>): Int

    fun delete(id: Long): Boolean

    fun generateStats(): List<MoldGachaStats>
}
