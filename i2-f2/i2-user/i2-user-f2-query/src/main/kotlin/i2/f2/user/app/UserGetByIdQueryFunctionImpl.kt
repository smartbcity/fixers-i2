package i2.f2.user.app

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import f2.dsl.fnc.invokeWith
import i2.f2.config.I2KeycloakConfig
import i2.f2.user.app.service.UserTransformer
import i2.f2.user.domain.features.query.UserGetByIdQuery
import i2.f2.user.domain.features.query.UserGetByIdQueryResult
import i2.f2.user.domain.features.query.UserGetByIdQueryFunction
import i2.keycloak.f2.user.domain.features.query.UserGetGroupsQuery
import i2.keycloak.f2.user.domain.features.query.UserGetGroupsQueryFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


// TODO Rename UserGetByIdQueryFunction as KeycloakUserGetByIdQueryFunction instead of using typealias
private typealias KeycloakUserGetByIdQueryFunction = i2.keycloak.f2.user.domain.features.query.UserGetByIdQueryFunction
private typealias KeycloakUserGetByIdQuery = i2.keycloak.f2.user.domain.features.query.UserGetByIdQuery

@Configuration
class UserGetByIdQueryFunctionImpl(
	private val userGetGroupsQueryFunction: UserGetGroupsQueryFunction,
	private val keycloakUserGetByIdQueryFunction: KeycloakUserGetByIdQueryFunction,
	private val i2KeycloakConfig: I2KeycloakConfig,
	private val userTransformer: UserTransformer
) {

	@Bean
	fun i2UserGetByIdQueryFunction(): UserGetByIdQueryFunction = f2Function { cmd ->
		keycloakUserGetByIdQueryFunction.invoke(cmd.toUserGetByIdQuery())
			.user
			?.let{userTransformer.toUser(it)}
			.let(::UserGetByIdQueryResult)
	}

	private fun UserGetByIdQuery.toUserGetByIdQuery() = KeycloakUserGetByIdQuery(
		id = id,
		realmId = i2KeycloakConfig.realm,
		auth = i2KeycloakConfig.authRealm()
	)
}
