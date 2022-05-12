package i2.f2.organization.app

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.f2.config.I2KeycloakConfig
import i2.f2.organization.app.model.toOrganization
import i2.f2.organization.domain.features.query.OrganizationGetFunction
import i2.f2.organization.domain.features.query.OrganizationGetQuery
import i2.f2.organization.domain.features.query.OrganizationGetResult
import i2.keycloak.f2.group.domain.features.query.GroupGetFunction
import i2.keycloak.f2.group.domain.features.query.GroupGetQuery
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrganizationGetFunctionImpl(
	private val groupGetQueryFunction: GroupGetFunction,
	private val i2KeycloakConfig: I2KeycloakConfig
) {

	@Bean
	fun organizationGetFunction(): OrganizationGetFunction = f2Function { cmd ->
		groupGetQueryFunction.invoke(cmd.toGroupGetByIdQuery())
			.item
			?.toOrganization()
			.let(::OrganizationGetResult)
	}

	private fun OrganizationGetQuery.toGroupGetByIdQuery() = GroupGetQuery(
		id = id,
		realmId = i2KeycloakConfig.realm,
		auth = i2KeycloakConfig.authRealm()
	)
}
