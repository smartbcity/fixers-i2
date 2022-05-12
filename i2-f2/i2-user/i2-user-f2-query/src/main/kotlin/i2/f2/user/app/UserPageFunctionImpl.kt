package i2.f2.user.app

import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.f2.config.I2KeycloakConfig
import i2.f2.user.app.service.UserTransformer
import i2.f2.user.domain.features.query.UserPageFunction
import i2.f2.user.domain.features.query.UserPageQuery
import i2.f2.user.domain.features.query.UserPageResult
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private typealias KeycloakUserPageQuery = i2.keycloak.f2.user.domain.features.query.UserPageQuery
private typealias KeycloakUserPageFunction = i2.keycloak.f2.user.domain.features.query.UserPageFunction

@Configuration
class UserPageFunctionImpl(
	private val i2KeycloakConfig: I2KeycloakConfig,
	private val keycloakUserPageFunction: KeycloakUserPageFunction,
	private val userTransformer: UserTransformer
) {

	@Bean
	fun i2UserPageFunction(): UserPageFunction = f2Function { cmd ->
		val query = keycloakUserPageFunction.invoke(cmd.toUserGetAllQuery()).items
		val users = query.items.map { user ->
			userTransformer.toUser(user)
		}
		UserPageResult(
			items = users,
			total = query.total
		)
	}

	private fun UserPageQuery.toUserGetAllQuery() = KeycloakUserPageQuery(
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
