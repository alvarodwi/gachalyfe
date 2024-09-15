package me.gachalyfe.rapi.controller.endpoint

import jakarta.validation.Valid
import me.gachalyfe.rapi.controller.ApiResponse
import me.gachalyfe.rapi.controller.buildResponse
import me.gachalyfe.rapi.controller.dto.BannerGachaDTO
import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.domain.model.BannerGacha
import me.gachalyfe.rapi.domain.service.BannerGachaService
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/gacha/banner")
class BannerGachaController(
    private val service: BannerGachaService,
) {
    private val log by lazyLogger()

    @GetMapping("latest")
    fun getRecents(): ResponseEntity<ApiResponse<List<BannerGacha>>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data retrieved successfully",
                data = service.findLatest(),
            )

        return response.buildResponse()
    }

    @GetMapping
    fun getByBannerName(
        @RequestParam("bannerName") bannerName: String,
    ): ResponseEntity<ApiResponse<List<BannerGacha>>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data retrieved successfully",
                data = service.findAllByBannerName(bannerName),
            )
        return response.buildResponse()
    }

    @GetMapping("{id}")
    fun getById(
        @PathVariable("id") id: Long,
    ): ResponseEntity<ApiResponse<BannerGacha>> {
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
        @Valid @RequestBody dto: BannerGachaDTO,
    ): ResponseEntity<ApiResponse<BannerGacha>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.CREATED.value(),
                message = "Data created successfully",
                data = service.save(dto.toModel()),
            )
        log.info("Created new banner gacha pull on ${dto.date} for ${dto.pickUpName} banner")
        return response.buildResponse()
    }

    @PutMapping("{id}")
    fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody dto: BannerGachaDTO,
    ): ResponseEntity<ApiResponse<BannerGacha>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data updated successfully",
                data = service.update(id, dto.toModel()),
            )
        log.info("Updated banner gacha pull with id $id on ${dto.date}")
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
        log.info("Deleted banner gacha pull with id $id")
        return response.buildResponse()
    }
}
