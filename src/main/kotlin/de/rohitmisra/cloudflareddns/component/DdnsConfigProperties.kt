package de.rohitmisra.cloudflareddns.component

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "hostnames")
class DdnsConfigProperties {
    var hostnames: List<String> = ArrayList()
}