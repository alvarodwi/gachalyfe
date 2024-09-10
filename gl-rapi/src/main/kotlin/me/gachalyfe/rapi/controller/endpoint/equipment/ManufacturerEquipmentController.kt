package me.gachalyfe.rapi.controller.endpoint.equipment

import jakarta.validation.Valid
import me.gachalyfe.rapi.controller.ApiResponse
import me.gachalyfe.rapi.controller.buildResponse
import me.gachalyfe.rapi.controller.dto.ManufacturerEquipmentDTO
import me.gachalyfe.rapi.data.mapper.EquipmentMapper
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
@RequestMapping("api/equipments")
class ManufacturerEquipmentController(
    private val service: ManufacturerEquipmentService,
    private val mapper: EquipmentMapper,
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
                data = service.updateEquipment(id, mapper.toModel(dto)),
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
