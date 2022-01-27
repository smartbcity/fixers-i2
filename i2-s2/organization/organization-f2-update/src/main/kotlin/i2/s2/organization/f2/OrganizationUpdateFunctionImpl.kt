package i2.s2.organization.f2

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.s2.commons.f2.utils.toJson
import i2.s2.group.domain.features.command.GroupUpdateCommand
import i2.s2.group.domain.features.command.GroupUpdateFunction
import i2.s2.organization.domain.features.command.OrganizationUpdateCommand
import i2.s2.organization.domain.features.command.OrganizationUpdateFunction
import i2.s2.organization.domain.features.command.OrganizationUpdatedResult
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
		).mapValues { (_, value) -> listOfNotNull(value) },
		roles = emptyList(),
		auth = auth,
		realmId = realmId
	)
}
