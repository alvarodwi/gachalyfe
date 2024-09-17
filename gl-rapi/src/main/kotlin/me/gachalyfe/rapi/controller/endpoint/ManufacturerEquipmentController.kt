package me.gachalyfe.rapi.controller.endpoint

import jakarta.validation.Valid
import me.gachalyfe.rapi.controller.ApiResponse
import me.gachalyfe.rapi.controller.Pagination
import me.gachalyfe.rapi.controller.buildResponse
import me.gachalyfe.rapi.controller.dto.ManufacturerArmsDTO
import me.gachalyfe.rapi.controller.dto.ManufacturerEquipmentDTO
import me.gachalyfe.rapi.controller.toPagination
import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.domain.model.EquipmentSourceType
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.model.stats.EquipmentSourceStats
import me.gachalyfe.rapi.domain.model.stats.EquipmentStats
import me.gachalyfe.rapi.domain.service.ManufacturerEquipmentService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/equipments")
class ManufacturerEquipmentController(
    private val service: ManufacturerEquipmentService,
) {
    @GetMapping
    fun getEquipments(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "date") sortBy: String,
        @RequestParam(defaultValue = "asc") sortDirection: String,
    ): ResponseEntity<ApiResponse<Pagination<ManufacturerEquipment>>> {
        val sort =
            if (sortDirection.equals("asc", ignoreCase = true)) {
                Sort.by(sortBy).ascending()
            } else {
                Sort.by(sortBy).descending()
            }
        val pageable = PageRequest.of(page, size, sort)
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data retrieved successfully",
                data = service.findAll(pageable).toPagination(),
            )
        return response.buildResponse()
    }

    @GetMapping("stats")
    fun getEquipmentStats(
        @RequestParam("sourceType") sourceType: String,
    ): ResponseEntity<ApiResponse<EquipmentStats>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Stats retrieved successfully",
                data = service.generateStats(EquipmentSourceType.valueOf(sourceType.uppercase())),
            )
        return response.buildResponse()
    }

    @GetMapping("stats/source")
    fun getEquipmentSourceStats(): ResponseEntity<ApiResponse<EquipmentSourceStats>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Stats retrieved successfully",
                data = service.generateSourceStats(),
            )
        return response.buildResponse()
    }

    @GetMapping("{type}")
    fun getEquipmentsBySourceType(
        @PathVariable("type") type: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "date") sortBy: String,
        @RequestParam(defaultValue = "asc") sortDirection: String,
    ): ResponseEntity<ApiResponse<Pagination<ManufacturerEquipment>>> {
        val sort =
            if (sortDirection.equals("asc", ignoreCase = true)) {
                Sort.by(sortBy).ascending()
            } else {
                Sort.by(sortBy).descending()
            }
        val pageable = PageRequest.of(page, size, sort)
        val sourceType = EquipmentSourceType.valueOf(type.uppercase())
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data retrieved successfully",
                data = service.findBySourceType(sourceType, pageable).toPagination(),
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
    fun updateEquipment(
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
    fun deleteEquipment(
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
