package i2.f2.organization.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.f2.organization.domain.model.OrganizationBase

/**
 * Gets an organization by Siret.
 * @d2 section
 * @parent [i2.f2.organization.domain.D2OrganizationQuerySection]
 */
typealias OrganizationGetBySiretFunction =
		F2Function<OrganizationGetBySiretQuery, OrganizationGetBySiretResult>

/**
 * @d2 query
 * @parent [OrganizationGetBySiretFunction]
 */
class OrganizationGetBySiretQuery(
	/**
	 * Siret number of the organization.
	 * @example [i2.f2.organization.domain.model.Organization.siret]
	 */
	val siret: String
): Command

/**
 * @d2 event
 * @parent [OrganizationGetBySiretFunction]
 */
class OrganizationGetBySiretResult(
	/**
	 * The organization.
	 */
	val item: OrganizationBase?
): Event
