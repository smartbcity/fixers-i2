package city.smartb.i2.spring.boot.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "i2")
@ConstructorBinding
data class I2TrustedIssuersConfig (
    private val issuers: List<I2TrustedIssuerProperties>
) {

    fun getTrustedIssuers(): List<String> {
        return issuers.map { it.uri }
    }
}
