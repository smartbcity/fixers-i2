package i2.s2.user.f2

import f2.dsl.fnc.f2Function
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.keycloak.realm.domain.UserModel
import i2.keycloak.realm.domain.features.query.UserGetByIdQueryFunction
import i2.keycloak.realm.domain.features.query.UserGetByIdQueryResult
import i2.s2.errors.I2ApiError
import i2.s2.errors.asI2Exception
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger
import javax.ws.rs.NotFoundException

@Configuration
class UserGetByIdQueryFunctionImpl {

	private val logger by Logger()

	@Bean
	fun userGetByIdQueryFunction(): UserGetByIdQueryFunction = f2Function { cmd ->
		val realmClient = AuthRealmClientBuilder().build(cmd.auth)
		try {
			realmClient.getUserResource(cmd.realmId, cmd.id)
				.toRepresentation()
				.asModel()
				.asResult()
		} catch (e: NotFoundException) {
			UserGetByIdQueryResult(null)
		} catch (e: Exception) {
			val msg = "Error fetching User with id[${cmd.id}]"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception()
		}
	}

	private fun UserModel.asResult(): UserGetByIdQueryResult {
		return UserGetByIdQueryResult(this)
	}
}
