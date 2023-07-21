package de.rohitmisra.cloudflareddns.model.web

import de.rohitmisra.cloudflareddns.model.entity.DnsRecord
import de.rohitmisra.cloudflareddns.model.entity.RETIRED
import de.rohitmisra.cloudflareddns.model.entity.STATUS

data class CreateDnsRecordRequest(
    val name: String,
    val comment: String
) {
    fun toDnsRecordEntity(): DnsRecord {
        return DnsRecord(
            name = this.name,
            comment = this.comment,
            status = STATUS.PENDING,
            retired = RETIRED.NO
        )
    }
}