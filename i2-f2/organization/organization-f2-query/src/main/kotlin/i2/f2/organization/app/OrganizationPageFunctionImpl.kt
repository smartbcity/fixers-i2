package i2.f2.organization.app

import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.f2.config.I2KeycloakConfig
import i2.f2.organization.app.model.toOrganization
import i2.f2.organization.domain.features.query.OrganizationPageFunction
import i2.f2.organization.domain.features.query.OrganizationPageQuery
import i2.f2.organization.domain.features.query.OrganizationPageResult
import i2.keycloak.f2.group.domain.features.query.GroupPageFunction
import i2.keycloak.f2.group.domain.features.query.GroupPageQuery
import i2.keycloak.f2.group.domain.model.GroupModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrganizationPageFunctionImpl(
	private val groupPageFunction: GroupPageFunction,
	private val i2KeycloakConfig: I2KeycloakConfig
) {

	@Bean
	fun organizationPageFunction(): OrganizationPageFunction = f2Function { cmd ->
		val query = groupPageFunction.invoke(cmd.toGroupGetAllQuery())

		OrganizationPageResult(
			items = query.page.items.map(GroupModel::toOrganization),
			total = query.page.total
		)
	}

	private fun OrganizationPageQuery.toGroupGetAllQuery() = GroupPageQuery(
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
