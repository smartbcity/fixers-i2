package i2.f2.user.app

import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.f2.config.I2KeycloakConfig
import i2.f2.organization.domain.features.query.OrganizationGetByIdQueryFunction
import i2.f2.user.app.model.toUser
import i2.f2.user.domain.features.query.UserGetAllQuery
import i2.f2.user.domain.features.query.UserGetAllQueryFunction
import i2.f2.user.domain.features.query.UserGetAllQueryResult
import i2.keycloak.f2.user.domain.features.query.UserGetPageQuery
import i2.keycloak.f2.user.domain.features.query.UserGetPageQueryFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserGetAllQueryFunctionImpl(
	private val organizationGetByIdQueryFunction: OrganizationGetByIdQueryFunction
	private val i2KeycloakConfig: I2KeycloakConfig,
	private val userGetPageQueryFunction: UserGetPageQueryFunction
) {

	@Bean
	fun userGetAllQueryFunction(): UserGetAllQueryFunction = f2Function { cmd ->
		if (cmd.organizationId == null) {
			getAllUsers(cmd)
		} else {
			getUsersOfOrganization(cmd)
		}
	}

	suspend fun getAllUsers(cmd: UserGetAllQuery): UserGetAllQueryResult {
		val userPage = userGetPageQueryFunction.invoke(cmd.toUserGetAllQuery())
			.page

		val users =	userPage.list
			.map { it.toUser(/* TODO need organizationRef */) }

		UserGetAllQueryResult(users, userPage.total)
	}

	suspend fun getUsersOfOrganization(cmd: UserGetAllQuery): UserGetAllQueryResult {
		// TODO there is a keycloak endpoint "group.members()" or something like that
	}

	private fun UserGetAllQuery.toUserGetAllQuery() = UserGetPageQuery(
		page = PagePagination(
			page = page,
			size = size
		),
		realmId = i2KeycloakConfig.realm,
		auth = i2KeycloakConfig.authRealm()
	)
}
