package me.gachalyfe.rapi.controller

import me.gachalyfe.rapi.domain.dto.AnomalyInterceptionDTO
import me.gachalyfe.rapi.domain.model.AnomalyInterception
import me.gachalyfe.rapi.domain.service.AnomalyInterceptionService
import me.gachalyfe.rapi.utils.lazyLogger
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
@RequestMapping("api/anomaly-interception-attempts")
class AnomalyInterceptionController(
    private val service: AnomalyInterceptionService,
) {
    private val log by lazyLogger()

    @GetMapping
    fun getAnomalyInterceptionAttempts(): ResponseEntity<List<AnomalyInterception>> {
        val data = service.getAttempts()
        return ResponseEntity.ok(data)
    }

    @PostMapping
    fun createAnomalyInterceptionAttempt(
        @RequestBody dto: AnomalyInterceptionDTO,
    ): ResponseEntity<AnomalyInterception> {
        val newAttempt = service.newAttempt(dto)
        return ResponseEntity.ok(newAttempt)
    }

    @PutMapping("{id}")
    fun updateAnomalyInterceptionAttempt(
        @PathVariable("id") id: Long,
        @RequestBody dto: AnomalyInterceptionDTO,
    ): ResponseEntity<AnomalyInterception> {
        val status = service.updateAttempt(id, dto)
        return ResponseEntity.ok(status)
    }

    @DeleteMapping("{id}")
    fun deleteAnomalyInterceptionAttempt(
        @PathVariable("id") id: Long,
    ) {
        service.deleteAttempt(id)
    }
}
