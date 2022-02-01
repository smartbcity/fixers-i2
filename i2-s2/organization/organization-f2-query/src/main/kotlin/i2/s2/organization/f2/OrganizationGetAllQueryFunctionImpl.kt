package i2.s2.organization.f2

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.s2.group.domain.features.query.GroupGetAllQuery
import i2.s2.group.domain.features.query.GroupGetAllQueryFunction
import i2.s2.group.domain.model.GroupModel
import i2.s2.organization.domain.features.query.OrganizationGetAllQuery
import i2.s2.organization.domain.features.query.OrganizationGetAllQueryFunction
import i2.s2.organization.domain.features.query.OrganizationGetAllQueryResult
import i2.s2.organization.f2.model.toOrganization
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrganizationGetAllQueryFunctionImpl(
	private val groupGetAllQueryFunction: GroupGetAllQueryFunction
) {

	@Bean
	fun organizationGetAllQueryFunction(): OrganizationGetAllQueryFunction = f2Function { cmd ->
		groupGetAllQueryFunction.invoke(cmd.toGroupGetAllQuery())
			.groups
			.map(GroupModel::toOrganization)
			.let(::OrganizationGetAllQueryResult)
	}

	private fun OrganizationGetAllQuery.toGroupGetAllQuery() = GroupGetAllQuery(
		search = search ?: "",
		page = page,
		size = size,
		realmId = realmId,
		auth = auth
	)
}
