package de.rohitmisra.cloudflareddns.component

import de.rohitmisra.cloudflareddns.model.dns.DnsRecord
import de.rohitmisra.cloudflareddns.model.zone.ZoneRecord
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.stream.Collectors
import javax.annotation.PostConstruct

@Component
@EnableScheduling
class CloudflareService(
    @Autowired private val cloudflareClient: CloudflareClient,
    @Autowired private val apiConfigProperties: ApiConfigProperties,
    @Autowired private val ddnsConfigProperties: DdnsConfigProperties,
    @Autowired private val ipAddressClient: IpAddressClient
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    private lateinit var email: String
    private lateinit var auth: String

    private val hostnameList = mutableListOf<String>()

    @PostConstruct
    fun setup() {
        email = apiConfigProperties.credentials["email"]!!
        auth = apiConfigProperties.credentials["authkey"]!!
        hostnameList.addAll(ddnsConfigProperties.hostnames)
    }

    @Scheduled(fixedDelay = 10 * 1_000L)
    fun scheduleFixedDelayTask() {
        log.info("Running cron job")
        log.info("Getting zone Id for $email")
        val zoneInfo = getZoneInfo(
            email = email,
            auth = auth
        )

        log.info("Got zone info as ${zoneInfo.id} : ${zoneInfo.name}")
        val ipv4Address = getIpv4Address()
        log.info("Resolved IP v4 address to be $ipv4Address")

        val existingDnsRecords = getDnsRecords(email, auth, zoneInfo.id)
        existingDnsRecords
            .filter { it.content != ipv4Address }
            .forEach {
                log.info("Updating DNS record for hostname: ${it.name}")
                updateDnsRecord(
                    email = email,
                    auth = auth,
                    zoneId = zoneInfo.id,
                    recordId = it.id,
                    ip = ipv4Address
                )
            }

        val existingHostnames = existingDnsRecords.stream()
            .map { it.name.replace(".${zoneInfo.name}", "") }
            .collect(Collectors.toList())

        val newDnsRecords = hostnameList.minus(
            existingHostnames
        )

        newDnsRecords.forEach {
            log.info("Setting DNS record for new hostname: $it")

            setDnsRecord(
                email = email,
                auth = auth,
                zoneId = zoneInfo.id,
                hostname = it,
                ip = ipv4Address
            )
        }
    }

    private fun getZoneInfo(email: String, auth: String): ZoneRecord {
        return cloudflareClient.getZone(email, auth)
    }

    private fun getDnsRecords(email: String, auth: String, zoneId: String): List<DnsRecord> {
        return cloudflareClient.getDnsRecords(email, auth, zoneId)
    }

    private fun updateDnsRecord(
        email: String,
        auth: String,
        zoneId: String,
        recordId: String,
        ip: String
    ) {
        cloudflareClient.updateDnsRecord(email, auth, zoneId, recordId, ip)
    }

    private fun setDnsRecord(email: String, auth: String, zoneId: String, hostname: String, ip: String) {
        cloudflareClient.setDnsRecord(email, auth, zoneId, hostname, ip)
    }

    private fun getIpv4Address(): String {
        return ipAddressClient.getIpv4().trim()
    }

}