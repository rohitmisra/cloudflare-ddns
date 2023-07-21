package de.rohitmisra.cloudflareddns.handler

import de.rohitmisra.cloudflareddns.model.web.CreateDnsRecordRequest
import de.rohitmisra.cloudflareddns.repository.DnsRecordRepository
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse

class DnsRecordHandler(
    private val dnsRecordRepository: DnsRecordRepository
) {
    fun createDnsRecord(
        request: ServerRequest
    ): ServerResponse =
        ServerResponse.ok()
            .body(dnsRecordRepository.save(request.body(CreateDnsRecordRequest::class.java).toDnsRecordEntity()))


    fun getAllDnsRecords(request: ServerRequest): ServerResponse =
        ServerResponse.ok().body(dnsRecordRepository.findAll().map { it.toCreateHostnameResponse() }.toList())
}