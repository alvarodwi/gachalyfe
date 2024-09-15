package me.gachalyfe.rapi.utils.csv.schema

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import me.gachalyfe.rapi.utils.csv.CsvListDeserializer
import java.time.LocalDate

class BannerGachaCsvSchema(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: LocalDate,
    val pickUpName: String,
    val gemsUsed: Int,
    val ticketUsed: Int,
    val totalAttempt: Int,
    val totalSSR: Int,
    @JsonDeserialize(using = CsvListDeserializer::class)
    val nikkePulled: List<String>,
)
