package de.rohitmisra.cloudflareddns.component

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import de.rohitmisra.cloudflareddns.model.dns.DnsCreateRequest
import de.rohitmisra.cloudflareddns.model.dns.DnsRecord
import de.rohitmisra.cloudflareddns.model.dns.DnsResponse
import de.rohitmisra.cloudflareddns.model.dns.DnsUpdateRequest
import de.rohitmisra.cloudflareddns.model.zone.ZoneRecord
import de.rohitmisra.cloudflareddns.model.zone.ZoneResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class CloudflareClient(
    private val webClient: OkHttpClient,
    private val objectMapper: ObjectMapper,
) {

    private val mediaTypeApplicationJson = MediaType.parse("application/json")
    private val log = LoggerFactory.getLogger(CloudflareClient::class.java)
    fun getZone(email: String, auth: String): ZoneRecord {
        val request = Request.Builder().url("https://api.cloudflare.com/client/v4/zones/")
            .addHeader("X-Auth-Email", email)
            .addHeader("X-Auth-Key", auth)
            .addHeader("Content-Type", "application/json")
            .get()
            .build()
        val jsonData = webClient.newCall(request).execute().body().string()
        val zoneResponse = objectMapper.readValue(jsonData, ZoneResponse::class.java)
        return zoneResponse.result[0]
    }

    fun getDnsRecords(email: String, auth: String, zoneId: String): List<DnsRecord> {
        val request = Request.Builder().url("https://api.cloudflare.com/client/v4/zones/$zoneId/dns_records")
            .addHeader("X-Auth-Email", email)
            .addHeader("X-Auth-Key", auth)
            .addHeader("Content-Type", "application/json")
            .get()
            .build()
        val jsonData = webClient.newCall(request).execute().body().string()
        val zoneResponse = objectMapper.readValue(jsonData, DnsResponse::class.java)
        return zoneResponse.result
    }

    fun updateDnsRecord(email: String, auth: String, zoneId: String, recordId: String, ip: String) {
        val dnsUpdateRequest = DnsUpdateRequest(
            content = ip
        )
        val requestBody = RequestBody.create(
            mediaTypeApplicationJson,
            objectMapper.writeValueAsString(dnsUpdateRequest)
        )
        val request = Request.Builder()
            .url("https://api.cloudflare.com/client/v4/zones/$zoneId/dns_records/$recordId")
            .addHeader("X-Auth-Email", email)
            .addHeader("X-Auth-Key", auth)
            .addHeader("Content-Type", "application/json")
            .patch(requestBody)
            .build()
        val response = webClient.newCall(request).execute().body().string()
        log.info("DNS Record for $recordId updated with $ip. Result: $response")
    }

    fun setDnsRecord(email: String, auth: String, zoneId: String, hostname: String, ip: String) {
        val dnsUpdateRequest = DnsCreateRequest(
            content = ip,
            name = hostname,
            proxied = true,
            ttl = 1,
            type = "A"
        )
        val requestBody = RequestBody.create(
            mediaTypeApplicationJson,
            objectMapper.writeValueAsString(dnsUpdateRequest)
        )
        val request = Request.Builder().url("https://api.cloudflare.com/client/v4/zones/$zoneId/dns_records")
            .addHeader("X-Auth-Email", email)
            .addHeader("X-Auth-Key", auth)
            .addHeader("Content-Type", "application/json")
            .post(requestBody)
            .build()
        val response = webClient.newCall(request).execute().body().string()
        log.info("DNS Record for $hostname updated with $ip. Result: $response")
    }
}