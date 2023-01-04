package city.smartb.i2.spring.boot.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "i2")
data class I2TrustedIssuersConfig (
    private val issuers: List<I2TrustedIssuerProperties> = emptyList(),
) {

    fun getTrustedIssuers(): List<String> {
        return issuers.map { it.uri }
    }
}
