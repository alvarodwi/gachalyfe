package me.gachalyfe.rapi.data.entity.gacha

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import me.gachalyfe.rapi.data.entity.NikkeEntity

@Entity(name = "banner_gacha")
data class BannerGachaEntity(
    @Id
    @Column(name = "id", columnDefinition = "INTEGER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val date: String,
    val pickUpName: String,
    val gemsUsed: Int,
    val ticketUsed: Int,
    val totalAttempt: Int,
    @Column(name = "total_ssr")
    val totalSSR: Int,
    @OneToMany(cascade = [CascadeType.MERGE], fetch = FetchType.LAZY)
    val nikkePulled: List<NikkeEntity> = emptyList(),
)
