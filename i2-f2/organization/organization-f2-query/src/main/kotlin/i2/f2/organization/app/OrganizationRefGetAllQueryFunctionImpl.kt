package i2.f2.organization.app

import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.f2.config.I2KeycloakConfig
import i2.f2.organization.app.model.toOrganizationRef
import i2.f2.organization.domain.features.query.OrganizationRefGetAllQuery
import i2.f2.organization.domain.features.query.OrganizationRefGetAllQueryFunction
import i2.f2.organization.domain.features.query.OrganizationRefGetAllQueryResult
import i2.keycloak.f2.group.domain.features.query.GroupGetAllQuery
import i2.keycloak.f2.group.domain.features.query.GroupGetAllQueryFunction
import i2.keycloak.f2.group.domain.model.GroupModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrganizationRefGetAllQueryFunctionImpl(
	private val groupGetAllQueryFunction: GroupGetAllQueryFunction,
	private val i2KeycloakConfig: I2KeycloakConfig
) {

	@Bean
	fun organizationRefGetAllQueryFunction(): OrganizationRefGetAllQueryFunction = f2Function { cmd ->
		groupGetAllQueryFunction.invoke(cmd.toGroupGetAllQuery())
			.groups
			.list
			.map(GroupModel::toOrganizationRef)
			.let(::OrganizationRefGetAllQueryResult)
	}

	private fun OrganizationRefGetAllQuery.toGroupGetAllQuery() = GroupGetAllQuery(
		name = null,
		role = null,
		page = PagePagination(
			page = null,
			size = null
		),
		realmId = i2KeycloakConfig.realm,
		auth = i2KeycloakConfig.authRealm()
	)
}
