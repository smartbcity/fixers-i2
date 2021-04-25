package i2.s2.user.f2

import f2.function.spring.adapter.f2Function
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.keycloak.realm.domain.features.command.*
import i2.s2.errors.I2ApiError
import i2.s2.errors.asI2Exception
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserDeleteFunctionImpl {

	@Bean
	fun userDeleteFunction(): UserDeleteFunction = f2Function { cmd ->
		try {
			val realmClient = AuthRealmClientBuilder().build(cmd.auth)
			realmClient.getUserResource(cmd.realmId, cmd.id).remove()
			UserDeletedResult(cmd.id)
		} catch (e: Exception) {
			throw I2ApiError(
				description = "Realm[${cmd.realmId}] Error deleting User[${cmd.id}]",
				payload = emptyMap()
			).asI2Exception(e)
		}
	}

}