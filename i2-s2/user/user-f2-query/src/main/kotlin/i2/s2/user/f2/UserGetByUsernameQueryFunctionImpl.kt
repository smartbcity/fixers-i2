package i2.s2.user.f2

import f2.dsl.fnc.f2Function
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.keycloak.realm.domain.UserModel
import i2.keycloak.realm.domain.features.query.UserGetByUsernameQueryFunction
import i2.keycloak.realm.domain.features.query.UserGetByUsernameQueryResult
import i2.s2.errors.I2ApiError
import i2.s2.errors.asI2Exception
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger

@Configuration
class UserGetByUsernameQueryFunctionImpl {

	private val logger by Logger()

	@Bean
	fun userGetByUsernameQueryFunction(): UserGetByUsernameQueryFunction = f2Function { cmd ->
		val realmClient = AuthRealmClientBuilder().build(cmd.auth)
		try {
			realmClient.users(cmd.realmId)
				.search(cmd.username)
				.firstOrNull()
				?.asModel()
				.asResult()
		} catch (e: NoSuchElementException) {
			UserGetByUsernameQueryResult(null)
		} catch (e: Exception) {
			val msg = "Error fetching User with username[${cmd.username}]"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception()
		}
	}

	private fun UserModel?.asResult(): UserGetByUsernameQueryResult {
		return UserGetByUsernameQueryResult(this)
	}
}
