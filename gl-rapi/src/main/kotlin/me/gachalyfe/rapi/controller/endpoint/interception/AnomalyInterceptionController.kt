package me.gachalyfe.rapi.controller.endpoint.interception

import jakarta.validation.Valid
import me.gachalyfe.rapi.controller.ApiResponse
import me.gachalyfe.rapi.controller.buildResponse
import me.gachalyfe.rapi.controller.dto.AnomalyInterceptionDTO
import me.gachalyfe.rapi.data.mapper.InterceptionMapper
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
    private val mapper: InterceptionMapper,
) {
    private val log by lazyLogger()

    @GetMapping
    fun getLast10(): ResponseEntity<ApiResponse<List<AnomalyInterception>>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data retrieved successfully",
                data = service.getAttempts(),
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
                data = service.getAttempt(id),
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
                data = service.createAttempt(mapper.toModel(dto)),
            )
        log.info("Created new anomaly interception attempt for ${dto.date} against ${dto.bossName}")
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
                data = service.updateAttempt(id, mapper.toModel(dto)),
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
                data = service.deleteAttempt(id),
            )
        log.info("Deleted anomaly interception attempt with id $id")
        return response.buildResponse()
    }
}
