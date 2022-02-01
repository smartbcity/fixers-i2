package i2.f2.organization.app

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.commons.utils.toJson
import i2.f2.organization.domain.features.command.OrganizationUpdateCommand
import i2.f2.organization.domain.features.command.OrganizationUpdateFunction
import i2.f2.organization.domain.features.command.OrganizationUpdatedResult
import i2.keycloak.f2.group.domain.features.command.GroupUpdateCommand
import i2.keycloak.f2.group.domain.features.command.GroupUpdateFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrganizationUpdateFunctionImpl(
	private val groupUpdateFunction: GroupUpdateFunction
) {

	@Bean
	fun organizationUpdateFunction(): OrganizationUpdateFunction = f2Function { cmd ->
		groupUpdateFunction.invoke(cmd.toGroupUpdateCommand())
			.let { result -> OrganizationUpdatedResult(result.id) }
	}

	private fun OrganizationUpdateCommand.toGroupUpdateCommand() = GroupUpdateCommand(
		id = id,
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
