package i2.config.api.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "i2")
class KcConfigProperties(
    var configPath: String
)
