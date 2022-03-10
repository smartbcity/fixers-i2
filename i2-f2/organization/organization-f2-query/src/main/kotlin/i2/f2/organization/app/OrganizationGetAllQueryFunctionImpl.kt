package i2.f2.organization.app

import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.f2.config.I2KeycloakConfig
import i2.f2.organization.app.model.toOrganization
import i2.f2.organization.domain.features.query.OrganizationGetAllQuery
import i2.f2.organization.domain.features.query.OrganizationGetAllQueryFunction
import i2.f2.organization.domain.features.query.OrganizationGetAllQueryResult
import i2.keycloak.f2.group.domain.features.query.GroupGetAllQuery
import i2.keycloak.f2.group.domain.features.query.GroupGetAllQueryFunction
import i2.keycloak.f2.group.domain.model.GroupModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrganizationGetAllQueryFunctionImpl(
	private val groupGetAllQueryFunction: GroupGetAllQueryFunction,
	private val i2KeycloakConfig: I2KeycloakConfig
) {

	@Bean
	fun organizationGetAllQueryFunction(): OrganizationGetAllQueryFunction = f2Function { cmd ->
		val query = groupGetAllQueryFunction.invoke(cmd.toGroupGetAllQuery())

		OrganizationGetAllQueryResult(
			organizations = query.groups.list.map(GroupModel::toOrganization),
			total = query.groups.total
		)
	}

	private fun OrganizationGetAllQuery.toGroupGetAllQuery() = GroupGetAllQuery(
		name = name,
		role = role,
		page = PagePagination(
			page = page,
			size = size
		),
		realmId = i2KeycloakConfig.realm,
		auth = i2KeycloakConfig.authRealm()
	)
}
