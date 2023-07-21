package de.rohitmisra.cloudflareddns.model.web

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class DnsRecordResponseDTO(
    val id: Long,
    val name: String,
    val comment: String,
    val status: STATUS,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime,
)
enum class STATUS {
    PENDING, SYNCED, SYNC_FAILED
}
