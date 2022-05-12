package i2.f2.user.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.f2.organization.domain.model.OrganizationId
import i2.f2.user.domain.model.UserBase

/**
 * Gets a page of users.
 * @d2 section
 * @parent [i2.f2.user.domain.D2UserQuerySection]
 */
typealias UserPageFunction = F2Function<UserPageQuery, UserPageResult>

/**
 * @d2 query
 * @parent [UserPageFunction]
 */
class UserPageQuery(
	/**
	 * Organization ID filter.
	 */
	val organizationId: OrganizationId?,

	/**
	 * Email filter.
	 */
	val email: String?,

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
 * @parent [UserPageFunction]
 */
class UserPageResult(
	/**
	 * List of users satisfying the requesting filters. The size of the list is lesser or equal than the requested size.
	 */
	val items: List<UserBase>,

	/**
	 * The total amount of users satisfying the requesting filters.
	 * @example 38
	 */
	val total: Int
): Event
