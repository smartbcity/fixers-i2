package i2.f2.organization.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.f2.organization.domain.model.OrganizationRef

/**
 * Gets all organization refs.
 * @d2 section
 * @parent [i2.f2.organization.domain.D2OrganizationRefQuerySection]
 */
typealias OrganizationRefGetAllFunction = F2Function<OrganizationRefGetAllQuery, OrganizationRefGetAllResult>

/**
 * @d2 query
 * @parent [OrganizationRefGetAllFunction]
 */
class OrganizationRefGetAllQuery: Command

/**
 * @d2 event
 * @parent [OrganizationRefGetAllFunction]
 */
class OrganizationRefGetAllResult(
	/**
	 * All Organization Refs.
	 */
	val items: List<OrganizationRef>
): Event
