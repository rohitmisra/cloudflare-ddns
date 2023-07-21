package de.rohitmisra.cloudflareddns.config

import de.rohitmisra.cloudflareddns.handler.DnsRecordHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.router

@Configuration
class Routes {
    @Bean
    fun appRouter(dnsRecordHandler: DnsRecordHandler) = router {
        "/dns-records".nest {
            "/".nest {
                POST(dnsRecordHandler::createDnsRecord)
                GET(dnsRecordHandler::getAllDnsRecords)
            }
        }
    }
}