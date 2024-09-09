package me.gachalyfe.rapi.controller

import jakarta.validation.Valid
import me.gachalyfe.rapi.data.dto.ApiResponse
import me.gachalyfe.rapi.data.dto.ManufacturerEquipmentDTO
import me.gachalyfe.rapi.data.dto.buildResponse
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.service.ManufacturerEquipmentService
import org.springframework.http.HttpStatus
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
    fun getLast10(): ResponseEntity<ApiResponse<List<ManufacturerEquipment>>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data retrieved successfully",
                data = service.getEquipments(),
            )
        return response.buildResponse()
    }

    @PutMapping("{id}")
    fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody dto: ManufacturerEquipmentDTO,
    ): ResponseEntity<ApiResponse<ManufacturerEquipment>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data updated successfully",
                data = service.updateEquipment(id, dto),
            )
        return response.buildResponse()
    }

    @DeleteMapping("{id}")
    fun delete(
        @PathVariable("id") id: Long,
    ): ResponseEntity<ApiResponse<Boolean>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.ACCEPTED.value(),
                message = "Data deleted successfully",
                data = service.deleteEquipment(id),
            )
        return response.buildResponse()
    }
}
