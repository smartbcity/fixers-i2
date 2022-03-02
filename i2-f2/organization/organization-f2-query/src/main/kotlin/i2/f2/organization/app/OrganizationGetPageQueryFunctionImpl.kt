package i2.f2.organization.app

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.f2.config.I2KeycloakConfig
import i2.f2.organization.app.model.toOrganization
import i2.f2.organization.domain.features.query.*
import i2.keycloak.f2.group.domain.features.query.GroupGetPageQuery
import i2.keycloak.f2.group.domain.features.query.GroupGetPageQueryFunction
import i2.keycloak.f2.group.domain.model.GroupModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrganizationGetPageQueryFunctionImpl(
	private val groupGetPageQueryFunction: GroupGetPageQueryFunction,
	private val i2KeycloakConfig: I2KeycloakConfig
) {

	@Bean
	fun organizationGetPageQueryFunction(): OrganizationGetPageQueryFunction = f2Function { cmd ->
		groupGetPageQueryFunction.invoke(cmd.toGroupGetPageQuery())
			.groups
			.map(GroupModel::toOrganization)
			.let(::OrganizationGetPageQueryResult)
	}

	private fun OrganizationGetPageQuery.toGroupGetPageQuery() = GroupGetPageQuery(
		name = name,
		role = role,
		page = page,
		size = size,
		realmId = i2KeycloakConfig.realm,
		auth = i2KeycloakConfig.authRealm()
	)
}
