package com.finanzuser.finanzuser.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
class CorsConfig : WebFluxConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").allowedMethods("POST", "PUT", "GET", "PATCH", "DELETE")
            .allowedOriginPatterns("*")
            .allowCredentials(true)
    }


}