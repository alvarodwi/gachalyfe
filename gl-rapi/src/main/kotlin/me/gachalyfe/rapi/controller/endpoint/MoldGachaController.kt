package me.gachalyfe.rapi.controller.endpoint

import jakarta.validation.Valid
import me.gachalyfe.rapi.controller.ApiResponse
import me.gachalyfe.rapi.controller.Pagination
import me.gachalyfe.rapi.controller.buildResponse
import me.gachalyfe.rapi.controller.dto.MoldGachaDTO
import me.gachalyfe.rapi.controller.toPagination
import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.domain.model.MoldGacha
import me.gachalyfe.rapi.domain.model.stats.MoldGachaStats
import me.gachalyfe.rapi.domain.service.MoldGachaService
import me.gachalyfe.rapi.utils.lazyLogger
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
@RequestMapping("api/gacha/mold")
class MoldGachaController(
    private val service: MoldGachaService,
) {
    private val log by lazyLogger()

    @GetMapping()
    fun getAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "date") sortBy: String,
        @RequestParam(defaultValue = "asc") sortDirection: String,
    ): ResponseEntity<ApiResponse<Pagination<MoldGacha>>> {
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
    fun getMoldGachaStats(): ResponseEntity<ApiResponse<List<MoldGachaStats>>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Stats retrieved successfully",
                data = service.generateStats(),
            )
        return response.buildResponse()
    }

    @GetMapping("{id}")
    fun getById(
        @PathVariable("id") id: Long,
    ): ResponseEntity<ApiResponse<MoldGacha>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data retrieved successfully",
                data = service.findById(id),
            )
        return response.buildResponse()
    }

    @PostMapping
    fun create(
        @Valid @RequestBody dto: MoldGachaDTO,
    ): ResponseEntity<ApiResponse<MoldGacha>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.CREATED.value(),
                message = "Data created successfully",
                data = service.save(dto.toModel()),
            )
        log.info("Created new mold opening on ${dto.date} with type ${dto.type}")
        return response.buildResponse()
    }

    @PutMapping("{id}")
    fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody dto: MoldGachaDTO,
    ): ResponseEntity<ApiResponse<MoldGacha>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data updated successfully",
                data = service.update(id, dto.toModel()),
            )
        log.info("Updated mold opening with id $id on ${dto.date}")
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
        log.info("Deleted mold opening with id $id")
        return response.buildResponse()
    }
}
