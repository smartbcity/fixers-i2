package i2.f2.organization.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.f2.organization.domain.model.OrganizationBase

typealias OrganizationGetPageQueryFunction = F2Function<OrganizationGetPageQuery, OrganizationGetPageQueryResult>

class OrganizationGetPageQuery(
	val name: String?,
	val role: String?,
	val page: Int,
	val size: Int
): Command

class OrganizationGetPageQueryResult(
	val organizations: List<OrganizationBase>
): Event
