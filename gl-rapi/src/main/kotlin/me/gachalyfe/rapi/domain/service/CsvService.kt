package me.gachalyfe.rapi.domain.service

import org.springframework.web.multipart.MultipartFile

interface CsvService {
    fun importCsv(
        file: MultipartFile,
        target: String,
    ): String

    fun exportCsv(target: String): ByteArray
}
