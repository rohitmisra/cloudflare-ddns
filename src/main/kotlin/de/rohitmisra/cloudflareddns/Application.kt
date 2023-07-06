package de.rohitmisra.cloudflareddns

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.squareup.okhttp.OkHttpClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application
fun main(args: Array<String>) {
    runApplication<Application>(*args)

    @Bean
    fun okHttpClient(): OkHttpClient = OkHttpClient()

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper().registerModule(KotlinModule())
}