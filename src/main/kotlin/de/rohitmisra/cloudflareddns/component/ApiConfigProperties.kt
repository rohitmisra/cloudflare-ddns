package de.rohitmisra.cloudflareddns.component

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "cloudflare-api")
class ApiConfigProperties {
    var credentials: Map<String, String> = HashMap()
}