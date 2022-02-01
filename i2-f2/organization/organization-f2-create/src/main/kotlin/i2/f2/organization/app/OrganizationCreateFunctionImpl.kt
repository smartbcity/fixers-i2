package i2.f2.organization.app

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.commons.utils.toJson
import i2.f2.organization.domain.features.command.OrganizationCreateCommand
import i2.f2.organization.domain.features.command.OrganizationCreateFunction
import i2.f2.organization.domain.features.command.OrganizationCreatedResult
import i2.keycloak.f2.group.domain.features.command.GroupCreateCommand
import i2.keycloak.f2.group.domain.features.command.GroupCreateFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrganizationCreateFunctionImpl(
	private val groupCreateFunction: GroupCreateFunction
) {

	@Bean
	fun organizationCreateFunction(): OrganizationCreateFunction = f2Function { cmd ->
		groupCreateFunction.invoke(cmd.toGroupCreateCommand())
			.let { result -> OrganizationCreatedResult(result.id) }
	}

	private fun OrganizationCreateCommand.toGroupCreateCommand() = GroupCreateCommand(
		name = name,
		attributes = mapOf(
			::siret.name to siret,
			::address.name to address.toJson(),
			::description.name to description,
			::website.name to website
		).mapValues { (_, value) -> listOfNotNull(value) },
		roles = emptyList(),
		auth = auth,
		realmId = realmId
	)
}
