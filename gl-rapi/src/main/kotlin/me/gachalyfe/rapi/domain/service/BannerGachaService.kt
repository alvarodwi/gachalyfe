package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.domain.model.BannerGacha
import me.gachalyfe.rapi.domain.model.stats.BannerGachaStats
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface BannerGachaService {
    fun findAll(pageable: Pageable): Page<BannerGacha>

    fun findAll(sort: Sort): List<BannerGacha>

    fun findByBannerName(
        bannerName: String,
        pageable: Pageable,
    ): Page<BannerGacha>

    fun findById(id: Long): BannerGacha

    fun save(model: BannerGacha): BannerGacha

    fun update(
        id: Long,
        model: BannerGacha,
    ): BannerGacha

    fun saveAll(data: List<BannerGacha>): Int

    fun delete(id: Long): Boolean

    fun generateStats(): List<BannerGachaStats>
}
