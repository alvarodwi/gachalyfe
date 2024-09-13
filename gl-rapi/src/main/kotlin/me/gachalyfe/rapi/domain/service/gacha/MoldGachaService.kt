package me.gachalyfe.rapi.domain.service.gacha

import me.gachalyfe.rapi.domain.model.gacha.MoldGacha
import me.gachalyfe.rapi.domain.model.gacha.MoldType

interface MoldGachaService {
    fun findAll(): List<MoldGacha>

    fun findAllByLatest(): List<MoldGacha>

    fun findAllByMoldType(moldType: MoldType): List<MoldGacha>

    fun findById(id: Long): MoldGacha

    fun save(model: MoldGacha): MoldGacha

    fun update(
        id: Long,
        model: MoldGacha,
    ): MoldGacha

    fun saveAll(data: List<MoldGacha>): Int

    fun delete(id: Long): Boolean
}
