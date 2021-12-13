package de.rohitmisra.cloudflareddns.model.zone

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ZoneRecord(
    val id: String,
    val name: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ZoneResponse(
    val result: List<ZoneRecord>
)