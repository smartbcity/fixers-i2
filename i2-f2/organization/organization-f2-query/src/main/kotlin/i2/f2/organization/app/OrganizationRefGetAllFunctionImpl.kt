package i2.f2.organization.app

import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.f2.config.I2KeycloakConfig
import i2.f2.organization.app.model.toOrganizationRef
import i2.f2.organization.domain.features.query.OrganizationRefGetAllFunction
import i2.f2.organization.domain.features.query.OrganizationRefGetAllQuery
import i2.f2.organization.domain.features.query.OrganizationRefGetAllResult
import i2.keycloak.f2.group.domain.features.query.GroupPageFunction
import i2.keycloak.f2.group.domain.features.query.GroupPageQuery
import i2.keycloak.f2.group.domain.model.GroupModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrganizationRefGetAllFunctionImpl(
	private val groupPageFunction: GroupPageFunction,
	private val i2KeycloakConfig: I2KeycloakConfig
) {

	@Bean
	fun organizationRefGetAllFunction(): OrganizationRefGetAllFunction = f2Function { cmd ->
		groupPageFunction.invoke(cmd.toGroupGetAllQuery())
			.page
			.items
			.map(GroupModel::toOrganizationRef)
			.let(::OrganizationRefGetAllResult)
	}

	private fun OrganizationRefGetAllQuery.toGroupGetAllQuery() = GroupPageQuery(
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
