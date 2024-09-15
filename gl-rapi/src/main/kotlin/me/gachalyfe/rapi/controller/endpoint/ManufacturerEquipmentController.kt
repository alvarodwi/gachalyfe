package me.gachalyfe.rapi.controller.endpoint

import jakarta.validation.Valid
import me.gachalyfe.rapi.controller.ApiResponse
import me.gachalyfe.rapi.controller.buildResponse
import me.gachalyfe.rapi.controller.dto.ManufacturerArmsDTO
import me.gachalyfe.rapi.controller.dto.ManufacturerEquipmentDTO
import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.service.ManufacturerEquipmentService
import org.springframework.http.HttpStatus
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
@RequestMapping("api/equipments")
class ManufacturerEquipmentController(
    private val service: ManufacturerEquipmentService,
) {
    @GetMapping
    fun getAll(): ResponseEntity<ApiResponse<List<ManufacturerEquipment>>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data retrieved successfully",
                data = service.findAll(),
            )
        return response.buildResponse()
    }

    @GetMapping("arms/latest")
    fun getLatestBySourceTypeArms(): ResponseEntity<ApiResponse<List<ManufacturerEquipment>>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data retrieved successfully",
                data = service.findRecentBySourceType(EquipmentSourceType.ARMS, 10),
            )
        return response.buildResponse()
    }

    @GetMapping("arms")
    fun getBySourceTypeArms(): ResponseEntity<ApiResponse<List<ManufacturerEquipment>>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data retrieved successfully",
                data = service.findAllBySourceType(EquipmentSourceType.ARMS),
            )
        return response.buildResponse()
    }

    @PostMapping("arms")
    fun saveManufacturerArms(
        @Valid @RequestBody dto: ManufacturerArmsDTO,
    ): ResponseEntity<ApiResponse<List<ManufacturerEquipment>>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.CREATED.value(),
                message = "Data created successfully",
                data = service.saveAll(dto.toModel()),
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
                data = service.update(id, dto.toModel()),
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
                data = service.delete(id),
            )
        return response.buildResponse()
    }
}
