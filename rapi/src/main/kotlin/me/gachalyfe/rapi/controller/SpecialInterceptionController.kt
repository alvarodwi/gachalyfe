package me.gachalyfe.rapi.controller

import jakarta.validation.Valid
import me.gachalyfe.rapi.data.dto.SpecialInterceptionDTO
import me.gachalyfe.rapi.domain.model.SpecialInterception
import me.gachalyfe.rapi.domain.service.SpecialInterceptionService
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
@RequestMapping("api/special-interceptions")
class SpecialInterceptionController(
    private val service: SpecialInterceptionService,
) {
    private val log by lazyLogger()

    @GetMapping
    fun getLast10(): ResponseEntity<List<SpecialInterception>> {
        val data = service.getAttempts()
        return ResponseEntity.ok(data)
    }

    @GetMapping("{id}")
    fun getById(
        @PathVariable("id") id: Long,
    ): ResponseEntity<SpecialInterception> {
        val data = service.getAttempt(id)
        return ResponseEntity.ok(data)
    }

    @PostMapping
    fun create(
        @Valid @RequestBody dto: SpecialInterceptionDTO,
    ): ResponseEntity<SpecialInterception> {
        val newAttempt = service.createAttempt(dto)
        log.info("Created new anomaly interception attempt for ${dto.date} against ${dto.bossName}")
        return ResponseEntity.ok(newAttempt)
    }

    @PutMapping("{id}")
    fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody dto: SpecialInterceptionDTO,
    ): ResponseEntity<SpecialInterception> {
        val status = service.updateAttempt(id, dto)
        log.info("Updated anomaly interception attempt with id $id on ${dto.date}")
        return ResponseEntity.ok(status)
    }

    @DeleteMapping("{id}")
    fun delete(
        @PathVariable("id") id: Long,
    ): ResponseEntity<Boolean> {
        val status = service.deleteAttempt(id)
        log.info("Deleted anomaly interception attempt with id $id")
        return ResponseEntity.ok(status)
    }
}
