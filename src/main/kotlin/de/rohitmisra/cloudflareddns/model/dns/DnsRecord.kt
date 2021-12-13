package de.rohitmisra.cloudflareddns.model.dns

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class DnsRecord(
    val content: String,
    val id: String,
    val name: String,
    val type: String,
    val zone_id: String,
    val zone_name: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class DnsResponse(
    val result: List<DnsRecord>
)