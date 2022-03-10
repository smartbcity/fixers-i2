package i2.f2.user.app

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.f2.config.I2KeycloakConfig
import i2.f2.user.app.model.toUser
import i2.f2.user.domain.features.query.UserGetByIdQuery
import i2.f2.user.domain.features.query.UserGetByIdQueryResult
import i2.f2.user.domain.features.query.UserGetByIdQueryFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private typealias KeycloakUserGetByIdQueryFunction = i2.keycloak.f2.user.domain.features.query.UserGetByIdQueryFunction
private typealias KeycloakUserGetByIdQuery = i2.keycloak.f2.user.domain.features.query.UserGetByIdQuery

@Configuration
class UserGetByIdQueryFunctionImpl(
	private val keycloakUserGetByIdQueryFunction: KeycloakUserGetByIdQueryFunction,
	private val i2KeycloakConfig: I2KeycloakConfig
) {

	@Bean
	fun i2UserGetByIdQueryFunction(): UserGetByIdQueryFunction = f2Function { cmd ->
		keycloakUserGetByIdQueryFunction.invoke(cmd.toUserGetByIdQuery())
			.user
			?.toUser()
			.let(::UserGetByIdQueryResult)
	}

	private fun UserGetByIdQuery.toUserGetByIdQuery() = KeycloakUserGetByIdQuery(
		id = id,
		realmId = i2KeycloakConfig.realm,
		auth = i2KeycloakConfig.authRealm()
	)
}
