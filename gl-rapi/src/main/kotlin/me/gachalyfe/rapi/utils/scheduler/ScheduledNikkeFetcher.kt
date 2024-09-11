package me.gachalyfe.rapi.utils.scheduler

import me.gachalyfe.rapi.data.mapper.toEntity
import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.data.repository.NikkeRepository
import me.gachalyfe.rapi.service.external.NikkedotggServiceClient
import me.gachalyfe.rapi.utils.lazyLogger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduledNikkeFetcher(
    private val nikkeClient: NikkedotggServiceClient,
    private val repository: NikkeRepository,
) {
    private val log by lazyLogger()

    @Scheduled(fixedDelayString = "PT24H")
    fun fetchNikke() {
        log.info("Fetching nikke from $nikkeClient")
        val data = nikkeClient.getNikke().map { it.toModel() }
        data.filter { it.id < 2000 }.forEach { nikke ->
            repository.save(nikke.toEntity())
        }
    }

}
