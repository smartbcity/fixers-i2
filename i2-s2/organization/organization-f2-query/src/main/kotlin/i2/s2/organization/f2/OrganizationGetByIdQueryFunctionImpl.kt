package i2.s2.organization.f2

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.s2.group.domain.features.query.GroupGetByIdQuery
import i2.s2.group.domain.features.query.GroupGetByIdQueryFunction
import i2.s2.organization.domain.features.query.OrganizationGetByIdQuery
import i2.s2.organization.domain.features.query.OrganizationGetByIdQueryFunction
import i2.s2.organization.domain.features.query.OrganizationGetByIdQueryResult
import i2.s2.organization.f2.model.toOrganization
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrganizationGetByIdQueryFunctionImpl(
	private val groupGetByIdQueryFunction: GroupGetByIdQueryFunction
) {

	@Bean
	fun organizationGetByIdQueryFunction(): OrganizationGetByIdQueryFunction = f2Function { cmd ->
		groupGetByIdQueryFunction.invoke(cmd.toGroupGetByIdQuery())
			.let { result -> OrganizationGetByIdQueryResult(result.group?.toOrganization()) }
	}

	private fun OrganizationGetByIdQuery.toGroupGetByIdQuery() = GroupGetByIdQuery(
		id = id,
		realmId = realmId,
		auth = auth
	)
}
