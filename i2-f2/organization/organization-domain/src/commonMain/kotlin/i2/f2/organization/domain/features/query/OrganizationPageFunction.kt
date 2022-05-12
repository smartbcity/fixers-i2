package i2.f2.organization.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.f2.organization.domain.model.OrganizationBase

/**
 * Gets a page of organizations.
 * @d2 section
 * @parent [i2.f2.organization.domain.D2OrganizationQuerySection]
 */
typealias OrganizationPageFunction = F2Function<OrganizationPageQuery, OrganizationPageResult>

/**
 * @d2 query
 * @parent [OrganizationPageFunction]
 */
class OrganizationPageQuery(
	/**
	 * Name filter.
	 * @example "SmartB"
	 */
	val name: String?,

	/**
	 * Role filter.
	 */
	val role: String?,

	/**
	 * Number of the page.
	 * @example 0
	 */
	val page: Int?,

	/**
	 * Size of the page.
	 * @example 10
	 */
	val size: Int?
): Command

/**
 * @d2 event
 * @parent [OrganizationPageFunction]
 */
class OrganizationPageResult(
	/**
	 * List of organizations satisfying the requesting filters. The size of the list is lesser or equal than the requested size.
	 */
	val items: List<OrganizationBase>,

	/**
	 * The total amount of users satisfying the requesting filters.
	 * @example 38
	 */
	val total: Int
): Event
