package i2.f2.user.app

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.f2.config.I2KeycloakConfig
import i2.f2.user.app.service.UserTransformer
import i2.f2.user.domain.features.query.UserGetFunction
import i2.f2.user.domain.features.query.UserGetQuery
import i2.f2.user.domain.features.query.UserGetResult
import i2.keycloak.f2.user.domain.features.query.UserGetGroupsFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


// TODO Rename UserGetByIdQueryFunction as KeycloakUserGetByIdQueryFunction instead of using typealias
private typealias KeycloakUserGetFunction = i2.keycloak.f2.user.domain.features.query.UserGetFunction
private typealias KeycloakUserGetQuery = i2.keycloak.f2.user.domain.features.query.UserGetQuery

@Configuration
class UserGetFunctionImpl(
	private val userGetGroupsFunction: UserGetGroupsFunction,
	private val keycloakUserGetFunction: KeycloakUserGetFunction,
	private val i2KeycloakConfig: I2KeycloakConfig,
	private val userTransformer: UserTransformer
) {

	@Bean
	fun i2UserGetFunction(): UserGetFunction = f2Function { cmd ->
		keycloakUserGetFunction.invoke(cmd.toUserGetQuery())
			.item
			?.let{userTransformer.toUser(it)}
			.let(::UserGetResult)
	}

	private fun UserGetQuery.toUserGetQuery() = KeycloakUserGetQuery(
		id = id,
		realmId = i2KeycloakConfig.realm,
		auth = i2KeycloakConfig.authRealm()
	)
}
