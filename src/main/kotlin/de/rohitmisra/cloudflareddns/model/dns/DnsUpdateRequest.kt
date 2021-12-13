package de.rohitmisra.cloudflareddns.model.dns

data class DnsCreateRequest(
    val content: String,
    val name: String,
    val proxied: Boolean,
    val ttl: Int,
    val type: String
)

data class DnsUpdateRequest(
    val content: String
)