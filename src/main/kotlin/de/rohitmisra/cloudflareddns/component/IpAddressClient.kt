package de.rohitmisra.cloudflareddns.component

import com.fasterxml.jackson.databind.ObjectMapper
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class IpAddressClient {

    private val log = LoggerFactory.getLogger(IpAddressClient::class.java)
    private val webClient = OkHttpClient()
    private val objectMapper = ObjectMapper()
    private val mediaTypeApplicationJson = MediaType.parse("application/json")

    fun getIpv4(): String {
        val request = Request.Builder().url("https://checkip.amazonaws.com/")
            .get()
            .build()
        return webClient.newCall(request).execute().body().string()
    }
}