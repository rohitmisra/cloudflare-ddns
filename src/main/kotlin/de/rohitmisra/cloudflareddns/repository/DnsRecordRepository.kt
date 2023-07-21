package de.rohitmisra.cloudflareddns.repository

import de.rohitmisra.cloudflareddns.model.entity.DnsRecord
import org.springframework.data.repository.CrudRepository

interface DnsRecordRepository : CrudRepository<DnsRecord, Long> {
}