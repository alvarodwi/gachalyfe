package me.gachalyfe.rapi.domain.service.gacha

import me.gachalyfe.rapi.domain.model.gacha.BannerGacha

interface BannerGachaService {
    fun findAll(): List<BannerGacha>

    fun findLatest(): List<BannerGacha>

    fun findAllByBannerName(bannerName: String): List<BannerGacha>

    fun findById(id: Long): BannerGacha

    fun save(model: BannerGacha): BannerGacha

    fun update(
        id: Long,
        model: BannerGacha,
    ): BannerGacha

    fun saveAll(data: List<BannerGacha>): Int

    fun delete(id: Long): Boolean
}
