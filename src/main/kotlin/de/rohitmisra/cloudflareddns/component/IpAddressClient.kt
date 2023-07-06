package de.rohitmisra.cloudflareddns.component

import com.fasterxml.jackson.databind.ObjectMapper
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class IpAddressClient(
    private val webClient: OkHttpClient,
    @Value("\${checkIpUrl}")
    private val checkIpUrl: String //https://checkip.amazonaws.com/

){
    fun getIpv4(): String {
        val request = Request.Builder().url(checkIpUrl)
            .get()
            .build()
        return webClient.newCall(request).execute().body().string()
    }
}