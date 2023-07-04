package i2.init.api.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(KcInitProperties::class)
class KcInitConfiguration {

}
