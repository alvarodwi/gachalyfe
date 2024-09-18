package me.gachalyfe.rapi.controller.endpoint

import jakarta.validation.Valid
import me.gachalyfe.rapi.controller.ApiResponse
import me.gachalyfe.rapi.controller.Pagination
import me.gachalyfe.rapi.controller.buildResponse
import me.gachalyfe.rapi.controller.dto.SpecialInterceptionDTO
import me.gachalyfe.rapi.controller.toPagination
import me.gachalyfe.rapi.domain.model.SpecialInterception
import me.gachalyfe.rapi.domain.model.stats.SpecialInterceptionStats
import me.gachalyfe.rapi.domain.service.SpecialInterceptionService
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
import toModel

@RestController
@RequestMapping("api/special-interceptions")
class SpecialInterceptionController(
    private val service: SpecialInterceptionService,
) {
    private val log by lazyLogger()

    @GetMapping
    fun getSpecialInterceptions(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "date") sortBy: String,
        @RequestParam(defaultValue = "asc") sortDirection: String,
    ): ResponseEntity<ApiResponse<Pagination<SpecialInterception>>> {
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
    fun getAnomalyInterceptionStats(
        @RequestParam("bossName") bossName: String,
    ): ResponseEntity<ApiResponse<SpecialInterceptionStats>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Stats retrieved successfully",
                data = service.generateStats(bossName),
            )
        return response.buildResponse()
    }

    @GetMapping("{id}")
    fun getSpecialInterceptionById(
        @PathVariable("id") id: Long,
    ): ResponseEntity<ApiResponse<SpecialInterception>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data retrieved successfully",
                data = service.findById(id),
            )
        return response.buildResponse()
    }

    @PostMapping
    fun createSpecialInterception(
        @Valid @RequestBody dto: SpecialInterceptionDTO,
    ): ResponseEntity<ApiResponse<SpecialInterception>> {
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
    fun updateSpecialInterception(
        @PathVariable("id") id: Long,
        @Valid @RequestBody dto: SpecialInterceptionDTO,
    ): ResponseEntity<ApiResponse<SpecialInterception>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data updated successfully",
                data = service.update(id, dto.toModel()),
            )
        log.info("Updated anomaly interception attempt with id $id on ${dto.date}")
        return response.buildResponse()
    }

    @DeleteMapping("{id}")
    fun deleteSpecialInterception(
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
