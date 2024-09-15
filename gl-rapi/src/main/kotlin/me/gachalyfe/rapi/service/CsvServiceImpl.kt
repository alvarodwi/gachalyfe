package me.gachalyfe.rapi.service

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvParser
import jakarta.transaction.Transactional
import me.gachalyfe.rapi.data.mapper.toCsvSchema
import me.gachalyfe.rapi.data.mapper.toModel
import me.gachalyfe.rapi.domain.model.AnomalyInterception
import me.gachalyfe.rapi.domain.model.BannerGacha
import me.gachalyfe.rapi.domain.model.ManufacturerEquipment
import me.gachalyfe.rapi.domain.model.MoldGacha
import me.gachalyfe.rapi.domain.model.SpecialInterception
import me.gachalyfe.rapi.domain.service.CsvService
import me.gachalyfe.rapi.domain.service.NikkeService
import me.gachalyfe.rapi.utils.csv.CsvHandler
import me.gachalyfe.rapi.utils.csv.schema.AnomalyInterceptionCsvSchema
import me.gachalyfe.rapi.utils.csv.schema.BannerGachaCsvSchema
import me.gachalyfe.rapi.utils.csv.schema.ManufacturerEquipmentCsvSchema
import me.gachalyfe.rapi.utils.csv.schema.MoldGachaCsvSchema
import me.gachalyfe.rapi.utils.csv.schema.SpecialInterceptionCsvSchema
import me.gachalyfe.rapi.utils.exception.CsvHandlingException
import me.gachalyfe.rapi.utils.lazyLogger
import org.springframework.context.ApplicationContext
import org.springframework.core.ResolvableType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import toCsvSchema
import toModel
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream

@Service
class CsvServiceImpl(
    private val applicationContext: ApplicationContext,
    private val nikkeService: NikkeService,
) : CsvService {
    private val log by lazyLogger()

    private inline fun <reified T> getCsvHandler(): CsvHandler<T> {
        val resolvableType = ResolvableType.forClassWithGenerics(CsvHandler::class.java, T::class.java)
        val beans = applicationContext.getBeansOfType(CsvHandler::class.java)
        for (bean in beans.values) {
            if (resolvableType.isAssignableFrom(ResolvableType.forInstance(bean))) {
                @Suppress("UNCHECKED_CAST")
                return bean as CsvHandler<T>
            }
        }
        throw IllegalArgumentException("No CsvHandler found for type: ${T::class.java}")
    }

    private val csvMapper =
        CsvMapper
            .csvBuilder()
            .enable(CsvParser.Feature.TRIM_SPACES)
            .enable(CsvParser.Feature.SKIP_EMPTY_LINES)
            .enable(CsvParser.Feature.FAIL_ON_MISSING_COLUMNS)
            .disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
            .findAndAddModules()
            .build()

    @Transactional
    override fun importCsv(
        file: MultipartFile,
        target: String,
    ): String {
        val inputStream = file.inputStream
        val totalImported =
            when (target) {
                "anomaly" -> {
                    val data =
                        readCsvData<AnomalyInterceptionCsvSchema>(inputStream)
                            .map { it.toModel() }
                    getCsvHandler<AnomalyInterception>().import(data)
                }

                "special" -> {
                    val data =
                        readCsvData<SpecialInterceptionCsvSchema>(inputStream)
                            .map { it.toModel() }
                    getCsvHandler<SpecialInterception>().import(data)
                }

                "equipment" -> {
                    val data =
                        readCsvData<ManufacturerEquipmentCsvSchema>(inputStream)
                            .map { it.toModel() }
                    getCsvHandler<ManufacturerEquipment>().import(data)
                }

                "banner-gacha" -> {
                    val data =
                        readCsvData<BannerGachaCsvSchema>(inputStream)
                            .map {
                                it.toModel({ names ->
                                    if (names.size == it.totalSSR) {
                                        names.map { name ->
                                            nikkeService.findAllByName(name, true).firstOrNull()
                                                ?: throw CsvHandlingException("Failed to parse nikke name: $name")
                                        }
                                    } else {
                                        throw CsvHandlingException(
                                            "Number of SSR and Nikke Pulled does not match for entry ${it.date}-${it.pickUpName}",
                                        )
                                    }
                                })
                            }
                    getCsvHandler<BannerGacha>().import(data)
                }

                "mold-gacha" -> {
                    val data =
                        readCsvData<MoldGachaCsvSchema>(inputStream)
                            .map {
                                it.toModel({ names ->
                                    if (names.size == it.totalSSR) {
                                        names.map { name ->
                                            nikkeService.findAllByName(name, true).firstOrNull()
                                                ?: throw CsvHandlingException("Failed to parse nikke name: $name")
                                        }
                                    } else {
                                        throw CsvHandlingException(
                                            "Number of SSR and Nikke Pulled does not match for entry ${it.date}-${it.type}",
                                        )
                                    }
                                })
                            }
                    getCsvHandler<MoldGacha>().import(data)
                }

                else -> throw CsvHandlingException("CSV import target unknown")
            }
        return "Imported $totalImported $target data"
    }

    override fun exportCsv(target: String): ByteArray {
        val outputStream = ByteArrayOutputStream()
        when (target) {
            "anomaly" ->
                writeCsvData(
                    getCsvHandler<AnomalyInterception>()
                        .export()
                        .map { it.toCsvSchema() },
                    outputStream,
                )

            "special" ->
                writeCsvData(
                    getCsvHandler<SpecialInterception>()
                        .export()
                        .map { it.toCsvSchema() },
                    outputStream,
                )

            "equipment" ->
                writeCsvData(
                    getCsvHandler<ManufacturerEquipment>()
                        .export()
                        .map { it.toCsvSchema() },
                    outputStream,
                )

            "banner-gacha" -> {
                writeCsvData(
                    getCsvHandler<BannerGacha>()
                        .export()
                        .map { it.toCsvSchema() },
                    outputStream,
                )
            }

            "mold-gacha" -> {
                writeCsvData(
                    getCsvHandler<MoldGacha>()
                        .export()
                        .map { it.toCsvSchema() },
                    outputStream,
                )
            }

            else -> throw CsvHandlingException("CSV export target unknown")
        }
        return outputStream.toByteArray()
    }

    private inline fun <reified T> writeCsvData(
        data: List<T>,
        outputStream: OutputStream,
    ) {
        if (data.isEmpty()) throw CsvHandlingException("Target data is empty, nothing to export")
        csvMapper
            .writer()
            .with(csvMapper.schemaFor(T::class.java).withHeader())
            .writeValues(outputStream)
            .writeAll(data)
    }

    private inline fun <reified T> readCsvData(inputStream: InputStream): List<T> {
        val schema = csvMapper.schemaFor(T::class.java)
        return csvMapper
            .readerFor(T::class.java)
            .with(schema.withSkipFirstDataRow(true))
            .readValues<T>(inputStream)
            .readAll()
    }
}
