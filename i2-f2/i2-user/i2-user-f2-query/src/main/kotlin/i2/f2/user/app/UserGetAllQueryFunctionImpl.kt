package i2.f2.user.app

import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.f2.config.I2KeycloakConfig
import i2.f2.organization.domain.model.OrganizationId
import i2.f2.organization.domain.model.OrganizationRefBase
import i2.f2.user.app.model.toUser
import i2.f2.user.domain.features.query.UserGetAllQuery
import i2.f2.user.domain.features.query.UserGetAllQueryFunction
import i2.f2.user.domain.features.query.UserGetAllQueryResult
import i2.keycloak.f2.group.domain.features.query.GroupGetByIdQuery
import i2.keycloak.f2.group.domain.features.query.GroupGetByIdQueryFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private typealias KeycloakUserGetAllQuery = i2.keycloak.f2.user.domain.features.query.UserGetAllQuery
private typealias KeycloakUserGetAllQueryFunction = i2.keycloak.f2.user.domain.features.query.UserGetAllQueryFunction

@Configuration
class UserGetAllQueryFunctionImpl(
	private val i2KeycloakConfig: I2KeycloakConfig,
	private val keycloakUserGetAllQueryFunction: KeycloakUserGetAllQueryFunction,
	private val groupGetByIdQueryFunction: GroupGetByIdQueryFunction
) {

	@Bean
	fun i2UserGetAllQueryFunction(): UserGetAllQueryFunction = f2Function { cmd ->
		val query = keycloakUserGetAllQueryFunction.invoke(cmd.toUserGetAllQuery()).users

		val users = query.list.map { user ->
			user.toUser(getOrganizationRef(user.attributes["memberOf"]?.first()))
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

	private suspend fun getOrganizationRef(organizationId: OrganizationId?): OrganizationRefBase? {
		if (organizationId.isNullOrEmpty()) {
			return null
		}

		val groupModel = groupGetByIdQueryFunction.invoke(GroupGetByIdQuery(
			id = organizationId,
			realmId = i2KeycloakConfig.realm,
			auth = i2KeycloakConfig.authRealm()
		)).group

		return groupModel?.let {
			OrganizationRefBase(
				id = it.id,
				name = it.name
			)
		}
	}
}
