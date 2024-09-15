package me.gachalyfe.rapi.utils.csv

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

class CsvListDeserializer : JsonDeserializer<List<String>>() {
    override fun deserialize(
        p: JsonParser,
        ctxt: DeserializationContext,
    ): List<String> {
        val value = p.text
        return if (value.isNotBlank()) {
            value.split(",").map { it.trim() }
        } else {
            emptyList()
        }
    }
}
