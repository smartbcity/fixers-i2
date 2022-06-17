package i2.f2.user.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.commons.model.AddressBase
import i2.f2.organization.domain.model.OrganizationId
import i2.f2.user.domain.model.UserId

/**
 * Updates the user.
 * @d2 section
 * @parent [i2.f2.user.domain.D2UserCommandSection]
 */
typealias UserUpdateFunction = F2Function<UserUpdateCommand, UserUpdateResult>

/**
 * @d2 command
 * @parent [UserUpdateFunction]
 */
data class UserUpdateCommand(
    /**
     * Identifier of the user.
     */
    val id: UserId,

    /**
     * Email address.
     * @example [i2.f2.user.domain.model.User.email]
     */
    val email: String,

    /**
     * First name of the user.
     * @example [i2.f2.user.domain.model.User.givenName]
     */
    val givenName: String,

    /**
     * Family name of the user.
     * @example [i2.f2.user.domain.model.User.familyName]
     */
    val familyName: String,

    /**
     * Address of the user.
     */
    val address: AddressBase?,

    /**
     * Telephone number of the user.
     * @example [i2.f2.user.domain.model.User.phone]
     */
    val phone: String?,

    /**
     * Send a validation email to the user.
     * @example [i2.f2.user.domain.model.User.sendEmailLink]
     */
    val sendEmailLink: Boolean,

    /**
     * Organization to which the user belongs.
     */
    val memberOf: OrganizationId?,

    /**
     * Roles assigned to the user.
     * @example [["admin"]]
     */
    val roles: List<String>,
): Command

/**
 * @d2 event
 * @parent [UserUpdateFunction]
 */
data class UserUpdateResult(
    /**
     * Identifier of the user.
     */
    val id: UserId
): Event
