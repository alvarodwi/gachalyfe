package me.gachalyfe.rapi.controller.endpoint

import jakarta.validation.Valid
import me.gachalyfe.rapi.controller.ApiResponse
import me.gachalyfe.rapi.controller.buildResponse
import me.gachalyfe.rapi.controller.dto.AnomalyInterceptionDTO
import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.domain.model.AnomalyInterception
import me.gachalyfe.rapi.domain.service.AnomalyInterceptionService
import me.gachalyfe.rapi.utils.lazyLogger
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
@RequestMapping("api/anomaly-interceptions")
class AnomalyInterceptionController(
    private val service: AnomalyInterceptionService,
) {
    private val log by lazyLogger()

    @GetMapping
    fun getAll(): ResponseEntity<ApiResponse<List<AnomalyInterception>>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data retrieved successfully",
                data = service.findAll(),
            )
        return response.buildResponse()
    }

    @GetMapping("latest")
    fun getLatest(): ResponseEntity<ApiResponse<List<AnomalyInterception>>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data retrieved successfully",
                data = service.findAllByLatest(),
            )
        return response.buildResponse()
    }

    @GetMapping("{id}")
    fun getById(
        @PathVariable("id") id: Long,
    ): ResponseEntity<ApiResponse<AnomalyInterception>> {
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
        @Valid @RequestBody dto: AnomalyInterceptionDTO,
    ): ResponseEntity<ApiResponse<AnomalyInterception>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.CREATED.value(),
                message = "Data created successfully",
                data = service.save(dto.toModel()),
            )
        log.info("Created new anomaly interception attempt on ${dto.date} against ${dto.bossName}")
        return response.buildResponse()
    }

    @PutMapping("{id}")
    fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody dto: AnomalyInterceptionDTO,
    ): ResponseEntity<ApiResponse<AnomalyInterception>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data updated successfully",
                data = service.delete(id, dto.toModel()),
            )
        log.info("Updated anomaly interception attempt with id $id on ${dto.date}")
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
        log.info("Deleted anomaly interception attempt with id $id")
        return response.buildResponse()
    }
}
