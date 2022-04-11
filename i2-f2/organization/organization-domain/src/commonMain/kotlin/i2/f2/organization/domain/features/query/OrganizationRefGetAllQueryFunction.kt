package i2.f2.organization.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.f2.organization.domain.model.OrganizationRef

typealias OrganizationRefGetAllQueryFunction = F2Function<OrganizationRefGetAllQuery, OrganizationRefGetAllQueryResult>

class OrganizationRefGetAllQuery: Command

class OrganizationRefGetAllQueryResult(
	val organizations: List<OrganizationRef>
): Event
