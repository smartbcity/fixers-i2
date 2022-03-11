package i2.f2.user.domain.model

import i2.commons.model.Address
import i2.commons.model.AddressBase
import i2.f2.organization.domain.model.OrganizationRef
import i2.f2.organization.domain.model.OrganizationRefBase
import i2.keycloak.f2.user.domain.model.UserRoles

typealias UserId = String

interface User {
    val id: UserId
    val memberOf: OrganizationRef?
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
    override val memberOf: OrganizationRefBase?,
    override val email: String,
    override val givenName: String,
    override val familyName: String,
    override val address: AddressBase?,
    override val phone: String?,
    override val roles: UserRoles,
    override val sendEmailLink: Boolean? = true
): User
