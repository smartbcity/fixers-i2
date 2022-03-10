package i2.f2.organization.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.f2.organization.domain.model.OrganizationBase

typealias OrganizationGetAllQueryFunction = F2Function<OrganizationGetAllQuery, OrganizationGetAllQueryResult>

class OrganizationGetAllQuery(
	val name: String?,
	val role: String?,
	val page: Int?,
	val size: Int?
): Command

class OrganizationGetAllQueryResult(
	val organizations: List<OrganizationBase>,
	val total: Int
): Event
