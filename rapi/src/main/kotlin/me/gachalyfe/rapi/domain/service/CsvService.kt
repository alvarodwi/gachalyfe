package me.gachalyfe.rapi.domain.service

import org.springframework.web.multipart.MultipartFile

interface CsvService {
    fun importFile(
        file: MultipartFile,
        target: String,
    ): Boolean

    fun exportFile(target: String): ByteArray
}
