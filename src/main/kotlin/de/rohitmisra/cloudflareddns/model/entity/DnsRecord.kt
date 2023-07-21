package de.rohitmisra.cloudflareddns.model.entity

import de.rohitmisra.cloudflareddns.model.web.DnsRecordResponseDTO
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
data class DnsRecord(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val name: String,
    val comment: String,
    @Enumerated(EnumType.STRING)
    val status: STATUS,
    @Enumerated(EnumType.STRING)
    val retired: RETIRED,
    @CreationTimestamp
    val createdAt: LocalDateTime? = null,
    @UpdateTimestamp
    val updatedAt: LocalDateTime? = null,
) {
    fun toCreateHostnameResponse() = DnsRecordResponseDTO(
        id = this.id?:0,
        name = this.name,
        comment = this.comment,
        status = de.rohitmisra.cloudflareddns.model.web.STATUS.valueOf(this.status.name),
        createdAt = this.createdAt?: LocalDateTime.now(),
        updatedAt = this.updatedAt?: LocalDateTime.now())
}
enum class STATUS {
    PENDING, SYNCED, SYNC_FAILED
}

enum class RETIRED {
    NO, MARKED, YES
}
