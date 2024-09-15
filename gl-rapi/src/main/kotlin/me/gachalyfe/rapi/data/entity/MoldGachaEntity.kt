package me.gachalyfe.rapi.data.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity(name = "mold_gacha")
data class MoldGachaEntity(
    @Id
    @Column(name = "id", columnDefinition = "INTEGER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val date: String,
    val type: Int,
    val amount: Int,
    @Column(name = "total_ssr")
    val totalSSR: Int,
    @OneToMany(cascade = [CascadeType.MERGE], fetch = FetchType.LAZY)
    val nikkePulled: List<NikkeEntity> = emptyList(),
)
