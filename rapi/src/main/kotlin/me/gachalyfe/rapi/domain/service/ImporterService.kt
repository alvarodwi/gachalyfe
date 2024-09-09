package me.gachalyfe.rapi.domain.service

import me.gachalyfe.rapi.domain.model.AnomalyInterception
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.model.SpecialInterception
import org.springframework.web.multipart.MultipartFile

interface ImporterService {
    fun importFile(file: MultipartFile, target: String) : Boolean
}
