package i2.f2.user.app

import i2.f2.config.I2KeycloakConfig
import i2.f2.user.app.service.UserTransformer
import i2.keycloak.f2.user.domain.features.query.UserGetGroupsQueryFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceAutoconfigure {

	@Bean
	fun userTransformer(
		i2KeycloakConfig: I2KeycloakConfig,
		userGetGroupsQueryFunction: UserGetGroupsQueryFunction
	): UserTransformer {
		return UserTransformer(i2KeycloakConfig, userGetGroupsQueryFunction)
	}
}
