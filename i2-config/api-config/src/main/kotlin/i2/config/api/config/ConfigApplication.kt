package i2.config.api.config

import i2.config.api.config.keycloak.KeycloakConfigResolver
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(KeycloakConfigResolver::class)
@Configuration(proxyBeanMethods = false)
@EntityScan("i2.config")
@SpringBootApplication(scanBasePackages = ["i2.config"])
class ConfigApplication

fun main(args: Array<String>) {
	SpringApplication(ConfigApplication::class.java).run {
		run(*args)
	}
}
