package me.gachalyfe.rapi.controller.endpoint

import me.gachalyfe.rapi.controller.ApiResponse
import me.gachalyfe.rapi.controller.Pagination
import me.gachalyfe.rapi.controller.buildResponse
import me.gachalyfe.rapi.controller.toPagination
import me.gachalyfe.rapi.domain.model.Nikke
import me.gachalyfe.rapi.domain.service.NikkeService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
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
    fun search(
        @RequestParam("name") name: String,
        @RequestParam("size", defaultValue = "5") size: Int,
    ): ResponseEntity<ApiResponse<Pagination<Nikke>>> {
        val sort = Sort.by("name")
        val pageable = PageRequest.of(0, size, sort)
        val response =
            ApiResponse.Success(
                status = HttpStatus.OK.value(),
                message = "Data retrieved successfully",
                data = service.findByNameLike(name, pageable).toPagination(),
            )
        return response.buildResponse()
    }
}
