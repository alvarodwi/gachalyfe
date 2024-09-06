package me.gachalyfe.rapi.controller

import me.gachalyfe.rapi.domain.dto.ManufacturerEquipmentDTO
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.service.ManufacturerEquipmentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/manufacturer-equipments")
class ManufacturerEquipmentController(
    private val service: ManufacturerEquipmentService
) {

    @GetMapping
    fun getAnomalyInterceptionAttempts(): ResponseEntity<List<ManufacturerEquipment>> {
        return ResponseEntity.ok(service.getEquipments())
    }

    @PostMapping
    fun createManufacturerEquipment(
        @RequestBody dto: ManufacturerEquipmentDTO,
    ): ResponseEntity<ManufacturerEquipment> {
        val newEquipment = service.addEquipment(dto)
        return ResponseEntity.ok(newEquipment)
    }

    @PutMapping("{id}")
    fun updateManufacturerEquipment(
        @PathVariable("id") id: Long,
        @RequestBody dto: ManufacturerEquipmentDTO,
    ): ResponseEntity<ManufacturerEquipment> {
        val updatedAttempt = service.updateEquipment(id, dto)
        return ResponseEntity.ok(updatedAttempt)
    }

    @DeleteMapping("{id}")
    fun deleteManufacturerEquipment(
        @PathVariable("id") id: Long,
    ): ResponseEntity<Boolean> {
        val status = service.deleteEquipment(id)
        return ResponseEntity.ok(status)
    }
}
