package me.gachalyfe.rapi.controller

import me.gachalyfe.rapi.domain.service.ImporterService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/import/csv")
class ImporterController(
    private val service: ImporterService,
) {
    @PostMapping("/anomaly-interceptions")
    fun importAnomalyInterceptionCsv() {
        TODO()
    }
}
