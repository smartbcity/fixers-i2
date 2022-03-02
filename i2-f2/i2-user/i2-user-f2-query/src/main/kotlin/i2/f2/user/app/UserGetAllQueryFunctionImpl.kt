package i2.f2.user.app

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.f2.config.I2KeycloakConfig
import i2.f2.user.app.model.toUser
import i2.f2.user.domain.features.query.UserGetAllQuery
import i2.f2.user.domain.features.query.UserGetAllQueryFunction
import i2.f2.user.domain.features.query.UserGetAllQueryResult
import i2.keycloak.f2.user.domain.features.query.UserGetByGroupIdQuery
import i2.keycloak.f2.user.domain.features.query.UserGetByGroupIdQueryFunction
import i2.keycloak.f2.user.domain.model.UserModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private typealias KeycloakUserGetAllQuery = i2.keycloak.f2.user.domain.features.query.UserGetAllQuery
private typealias KeycloakUserGetAllQueryFunction = i2.keycloak.f2.user.domain.features.query.UserGetAllQueryFunction

@Configuration
class UserGetAllQueryFunctionImpl(
	private val i2KeycloakConfig: I2KeycloakConfig,
	private val keycloakUserGetAllQueryFunction: KeycloakUserGetAllQueryFunction,
	private val userGetByGroupIdQueryFunction: UserGetByGroupIdQueryFunction
) {

	@Bean
	fun i2UserGetAllQueryFunction(): UserGetAllQueryFunction = f2Function { cmd ->
		if (cmd.organizationId == null) {
			getAllUsers(cmd)
		} else {
			getUsersOfOrganization(cmd)
		}
	}

	suspend fun getAllUsers(cmd: UserGetAllQuery): UserGetAllQueryResult {
		val query = keycloakUserGetAllQueryFunction.invoke(cmd.toUserGetAllQuery())
			.users

		// TODO add organizationRef
		return UserGetAllQueryResult(
			users = query.list.map(UserModel::toUser),
			total = query.total
		)
	}

	suspend fun getUsersOfOrganization(cmd: UserGetAllQuery): UserGetAllQueryResult {
		val query = userGetByGroupIdQueryFunction.invoke(cmd.toUserGetByGroupIdQuery())
			.users

		// TODO add organizationRef
		return UserGetAllQueryResult(
			users = query.list.map(UserModel::toUser),
			total = query.total
		)
	}

	private fun UserGetAllQuery.toUserGetAllQuery() = KeycloakUserGetAllQuery(
		page = page,
		size = size,
		realmId = i2KeycloakConfig.realm,
		auth = i2KeycloakConfig.authRealm()
	)

	private fun UserGetAllQuery.toUserGetByGroupIdQuery() = UserGetByGroupIdQuery(
		groupId = organizationId!!,
		page = page,
		size = size,
		realmId = i2KeycloakConfig.realm,
		auth = i2KeycloakConfig.authRealm()
	)
}
