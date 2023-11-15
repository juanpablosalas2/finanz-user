package com.finanzuser.finanzuser

import com.finanzuser.finanzuser.config.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@EnableR2dbcRepositories
@EnableConfigurationProperties(JwtProperties::class)
class FinanzUserApplication

fun main(args: Array<String>) {
    runApplication<FinanzUserApplication>(*args)
}
