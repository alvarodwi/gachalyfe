package me.gachalyfe.rapi.controller.endpoint

import me.gachalyfe.rapi.controller.ApiResponse
import me.gachalyfe.rapi.controller.buildResponse
import me.gachalyfe.rapi.domain.model.Nikke
import me.gachalyfe.rapi.domain.service.NikkeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/nikke")
class NikkeController(
    private val service: NikkeService,
) {
    @GetMapping
    fun getAll(
        @RequestParam("name") name: String?,
    ): ResponseEntity<ApiResponse<List<Nikke>>> {
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data retrieved successfully",
                data = name?.let { service.findAllByName(name) } ?: service.findAll(),
            )
        return response.buildResponse()
    }
}
