package i2.f2.user.app

import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.f2.config.I2KeycloakConfig
import i2.f2.user.app.service.UserTransformer
import i2.f2.user.domain.features.query.UserGetAllQuery
import i2.f2.user.domain.features.query.UserGetAllQueryFunction
import i2.f2.user.domain.features.query.UserGetAllQueryResult
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private typealias KeycloakUserGetAllQuery = i2.keycloak.f2.user.domain.features.query.UserGetAllQuery
private typealias KeycloakUserGetAllQueryFunction = i2.keycloak.f2.user.domain.features.query.UserGetAllQueryFunction

@Configuration
class UserGetAllQueryFunctionImpl(
	private val i2KeycloakConfig: I2KeycloakConfig,
	private val keycloakUserGetAllQueryFunction: KeycloakUserGetAllQueryFunction,
	private val userTransformer: UserTransformer
) {

	@Bean
	fun i2UserGetAllQueryFunction(): UserGetAllQueryFunction = f2Function { cmd ->
		val query = keycloakUserGetAllQueryFunction.invoke(cmd.toUserGetAllQuery()).users
		val users = query.items.map { user ->
			userTransformer.toUser(user)
		}
		UserGetAllQueryResult(
			users = users,
			total = query.total
		)
	}

	private fun UserGetAllQuery.toUserGetAllQuery() = KeycloakUserGetAllQuery(
		groupId = organizationId,
		email = email,
		role = role,
		page = PagePagination(
			page = page,
			size = size
		),
		realmId = i2KeycloakConfig.realm,
		auth = i2KeycloakConfig.authRealm()
	)


}
