package i2.f2.user.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.f2.user.domain.model.UserBase
import i2.f2.user.domain.model.UserId

/**
 * Gets a user by ID.
 * @d2 section
 * @parent [i2.f2.user.domain.D2UserQuerySection]
 */
typealias UserGetFunction = F2Function<UserGetQuery, UserGetResult>

/**
 * @d2 query
 * @parent [UserGetFunction]
 */
class UserGetQuery(
    /**
     * Identifier of the user.
     */
    val id: UserId
): Command

/**
 * @d2 event
 * @parent [UserGetFunction]
 */
class UserGetResult(
    /**
     * The user.
     */
	val item: UserBase?
): Event
