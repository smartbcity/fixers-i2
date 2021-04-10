package i2.test.bdd.testcontainers

import org.springframework.core.io.ClassPathResource


open class I2KeycloakTest {

	var i2KeycloakContainer: I2KeycloakContainer =
		I2KeycloakContainer.getInstance(ClassPathResource("docker-compose-keycloak-it.yml").file)
}