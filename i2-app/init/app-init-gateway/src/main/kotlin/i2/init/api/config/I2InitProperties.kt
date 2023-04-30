package i2.init.api.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("i2.init")
class I2InitProperties(
    val maxRetries: Int,
    val retryDelayMillis: Long
)
