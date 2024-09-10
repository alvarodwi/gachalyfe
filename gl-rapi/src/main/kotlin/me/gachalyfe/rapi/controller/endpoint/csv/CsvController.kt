package me.gachalyfe.rapi.controller.endpoint.csv

import jakarta.servlet.http.HttpServletResponse
import me.gachalyfe.rapi.controller.ApiResponse
import me.gachalyfe.rapi.controller.buildResponse
import me.gachalyfe.rapi.domain.service.CsvService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("api/csv")
class CsvController(
    private val service: CsvService,
) {
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun importCsv(
        @RequestPart("file") file: MultipartFile,
        @RequestParam("target") target: String,
    ): ResponseEntity<ApiResponse<Boolean>> {
        if (file.isEmpty) {
            return ApiResponse
                .Error(
                    status = HttpStatus.BAD_REQUEST.value(),
                    message = "CSV file is empty",
                ).buildResponse()
        }

        return ApiResponse
            .Success(
                status = HttpStatus.OK.value(),
                message = "CSV imported successfully",
                data = service.importFile(file, target),
            ).buildResponse()
    }

    @GetMapping
    @Throws(IllegalStateException::class)
    fun exportCsv(
        @RequestParam("target") target: String,
        response: HttpServletResponse,
    ): ResponseEntity<ByteArray> =
        ResponseEntity
            .ok()
            .contentType(MediaType("text", "csv"))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export-$target.csv")
            .body(service.exportFile(target))
}
