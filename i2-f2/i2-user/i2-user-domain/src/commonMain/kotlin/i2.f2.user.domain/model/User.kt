package i2.f2.user.domain.model

import i2.commons.model.Address
import i2.commons.model.AddressBase
import i2.f2.organization.domain.model.OrganizationRefDTO
import i2.f2.organization.domain.model.OrganizationRef
import i2.keycloak.f2.user.domain.model.UserRoles

/**
 * Unique identifier of an user.
 * @d2 model
 * @parent [i2.f2.user.domain.D2UserModelSection]
 * @order 20
 * @visual json "e8322a0b-b4cf-4643-a398-c442d22504be"
 */
typealias UserId = String

/**
 * Representation of an user.
 * @D2 model
 * @parent [i2.f2.user.domain.D2UserModelSection]
 */
interface User {
    /**
     * Identifier of an user.
     * @example "e8322a0b-b4cf-4643-a398-c442d22504be"
     */
    val id: UserId
    /**
     * Organization an user belongs to.
     */
    val memberOf: OrganizationRefDTO?
    val email: String
    val givenName: String
    val familyName: String
    val address: Address?
    val phone: String?
    val roles: UserRoles
    val sendEmailLink: Boolean?
}

data class UserBase(
    override val id: UserId,
    override val memberOf: OrganizationRef?,
    override val email: String,
    override val givenName: String,
    override val familyName: String,
    override val address: AddressBase?,
    override val phone: String?,
    override val roles: UserRoles,
    override val sendEmailLink: Boolean? = true
): User
