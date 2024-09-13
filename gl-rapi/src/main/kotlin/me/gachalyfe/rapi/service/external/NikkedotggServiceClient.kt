package me.gachalyfe.rapi.service.external

import me.gachalyfe.rapi.controller.dto.NikkeDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class NikkedotggServiceClient(
    @Value("\${service.nikkedotgg.host}") private val clientUrl: String,
) {
    fun getNikke(): List<NikkeDTO> {
        val restTemplate = RestTemplate()
        val responseType = object : ParameterizedTypeReference<List<NikkeDTO>>() {}
        val response = restTemplate.exchange<List<NikkeDTO>>("$clientUrl/characters", HttpMethod.GET, null, responseType)

        return response.body ?: emptyList()
    }
}
