package i2.s2.user.f2

import f2.dsl.fnc.f2Function
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.keycloak.realm.domain.features.command.*
import i2.s2.errors.I2ApiError
import i2.s2.errors.asI2Exception
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserDisableFunctionImpl {

	@Bean
	fun userDisableFunction(): UserDisableFunction = f2Function { cmd ->
		try {
			val realmClient = AuthRealmClientBuilder().build(cmd.auth)
			val userResource = realmClient.getUserResource(cmd.realmId, cmd.id)

			val representation = userResource.toRepresentation().apply {
				isEnabled = false
			}

			userResource.update(representation)
			UserDisabledResult(cmd.id)
		} catch (e: Exception) {
			throw I2ApiError(
				description = "Realm[${cmd.realmId}] User[${cmd.id}] Error disabling",
				payload = emptyMap()
			).asI2Exception(e)
		}
	}

}