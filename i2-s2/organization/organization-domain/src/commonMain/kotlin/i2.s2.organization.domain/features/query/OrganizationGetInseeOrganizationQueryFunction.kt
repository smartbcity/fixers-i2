package i2.s2.organization.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.s2.organization.domain.model.OrganizationBase

typealias OrganizationGetInseeOrganizationQueryFunction =
		F2Function<OrganizationGetInseeOrganizationQuery, OrganizationGetInseeOrganizationQueryResult>

class OrganizationGetInseeOrganizationQuery(
	val siret: String
): Command

class OrganizationGetInseeOrganizationQueryResult(
	val organization: OrganizationBase?
): Event
