package i2.f2.organization.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.f2.organization.domain.model.OrganizationBase
import i2.f2.organization.domain.model.OrganizationId

typealias OrganizationGetByIdQueryFunction = F2Function<OrganizationGetByIdQuery, OrganizationGetByIdQueryResult>

class OrganizationGetByIdQuery(
    val id: OrganizationId
): Command

class OrganizationGetByIdQueryResult(
	val organization: OrganizationBase?
): Event
