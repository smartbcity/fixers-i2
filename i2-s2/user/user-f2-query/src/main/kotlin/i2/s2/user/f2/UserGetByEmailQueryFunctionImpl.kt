package i2.s2.user.f2

import f2.function.spring.adapter.f2Function
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.keycloak.realm.domain.UserModel
import i2.keycloak.realm.domain.features.query.UserGetByEmailQueryFunction
import i2.keycloak.realm.domain.features.query.UserGetByEmailQueryResult
import i2.s2.errors.I2ApiError
import i2.s2.errors.asI2Exception
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger

@Configuration
class UserGetByEmailQueryFunctionImpl {

	private val logger by Logger()

	@Bean
	fun userGetByEmailQueryFunction(): UserGetByEmailQueryFunction = f2Function { cmd ->
		val realmClient = AuthRealmClientBuilder().build(cmd.auth)
		try {
			realmClient.users(cmd.realmId).list().first { user -> user.email == cmd.email }
				.asModel()
				.asResult()
		} catch (e: NoSuchElementException) {
			UserGetByEmailQueryResult(null)
		} catch (e: Exception) {
			val msg = "Error fetching User with email[${cmd.email}]"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception()
		}
	}

	private fun UserModel.asResult(): UserGetByEmailQueryResult {
		return UserGetByEmailQueryResult(this)
	}

}