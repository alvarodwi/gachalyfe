package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.domain.model.AnomalyInterception
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.model.SpecialInterception

interface ImporterService {
    fun importAnomalyInterceptionData(data: List<AnomalyInterception>)

    fun importSpecialInterceptionData(data: List<SpecialInterception>)

    fun importManufacturerEquipmentData(data: List<ManufacturerEquipment>)
}
