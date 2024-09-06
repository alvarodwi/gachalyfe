package me.gachalyfe.rapi.data.impl

import me.gachalyfe.rapi.domain.model.AnomalyInterception
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.model.SpecialInterception
import me.gachalyfe.rapi.domain.service.ImporterService
import org.springframework.stereotype.Service

@Service
class ImporterServiceImpl : ImporterService {
    override fun importAnomalyInterceptionData(data: List<AnomalyInterception>) {
        TODO("Not yet implemented")
    }

    override fun importSpecialInterceptionData(data: List<SpecialInterception>) {
        TODO("Not yet implemented")
    }

    override fun importManufacturerEquipmentData(data: List<ManufacturerEquipment>) {
        TODO("Not yet implemented")
    }

}
