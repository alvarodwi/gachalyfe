package me.gachalyfe.rapi.controller

import jakarta.validation.Valid
import me.gachalyfe.rapi.data.dto.ManufacturerEquipmentDTO
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.service.ManufacturerEquipmentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/manufacturer-equipments")
class ManufacturerEquipmentController(
    private val service: ManufacturerEquipmentService,
) {
    @GetMapping
    fun getLast10(): ResponseEntity<List<ManufacturerEquipment>> = ResponseEntity.ok(service.getEquipments())

    @PutMapping("{id}")
    fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody dto: ManufacturerEquipmentDTO,
    ): ResponseEntity<ManufacturerEquipment> {
        val updatedAttempt = service.updateEquipment(id, dto)
        return ResponseEntity.ok(updatedAttempt)
    }

    @DeleteMapping("{id}")
    fun delete(
        @PathVariable("id") id: Long,
    ): ResponseEntity<Boolean> {
        val status = service.deleteEquipment(id)
        return ResponseEntity.ok(status)
    }
}
