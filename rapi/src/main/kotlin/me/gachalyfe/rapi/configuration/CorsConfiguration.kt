package me.gachalyfe.rapi.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfiguration : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedOrigins("http://localhost:5173")
            .allowedOrigins("http://gachalyfe.local")
            .allowedHeaders("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowCredentials(true)
    }
}
