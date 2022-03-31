package i2.f2.organization.app

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import f2.dsl.fnc.invokeWith
import i2.commons.utils.toJson
import i2.f2.config.I2KeycloakConfig
import i2.f2.organization.domain.features.command.OrganizationUpdateCommand
import i2.f2.organization.domain.features.command.OrganizationUpdateFunction
import i2.f2.organization.domain.features.command.OrganizationUpdatedResult
import i2.f2.organization.domain.features.query.OrganizationGetByIdQuery
import i2.f2.organization.domain.features.query.OrganizationGetByIdQueryFunction
import i2.f2.organization.domain.model.Organization
import i2.keycloak.f2.group.domain.features.command.GroupUpdateCommand
import i2.keycloak.f2.group.domain.features.command.GroupUpdateFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.ws.rs.NotFoundException

@Configuration
class OrganizationUpdateFunctionImpl(
	private val groupUpdateFunction: GroupUpdateFunction,
	private val i2KeycloakConfig: I2KeycloakConfig,
	private val organizationGetByIdQueryFunction: OrganizationGetByIdQueryFunction
) {

	@Bean
	fun organizationUpdateFunction(): OrganizationUpdateFunction = f2Function { cmd ->
		val organization = OrganizationGetByIdQuery(id = cmd.id)
			.invokeWith(organizationGetByIdQueryFunction)
			.organization
			?: throw NotFoundException("Organization [${cmd.id}] not found")

		groupUpdateFunction.invoke(cmd.toGroupUpdateCommand(organization))
			.let { result -> OrganizationUpdatedResult(result.id) }
	}

	private fun OrganizationUpdateCommand.toGroupUpdateCommand(organization: Organization) = GroupUpdateCommand(
		id = id,
		name = name,
		attributes = mapOf(
			organization::siret.name to organization.siret,
			::address.name to address.toJson(),
			::description.name to description,
			::website.name to website
		).mapValues { (_, value) -> listOfNotNull(value) },
		roles = roles ?: emptyList(),
		realmId = i2KeycloakConfig.realm,
		auth = i2KeycloakConfig.authRealm()
	)
}
