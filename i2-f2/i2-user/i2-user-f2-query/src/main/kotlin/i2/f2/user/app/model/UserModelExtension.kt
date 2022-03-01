package i2.f2.user.app.model

import i2.commons.model.AddressBase
import i2.commons.utils.parseJsonTo
import i2.f2.organization.app.model.orEmpty
import i2.f2.organization.domain.model.OrganizationRefBase
import i2.f2.user.domain.model.UserBase
import i2.keycloak.f2.user.domain.model.UserModel

fun UserModel.toUser(organizationRef: OrganizationRefBase? = null) = UserBase(
    id = id,
    memberOf = organizationRef,
    email = email ?: "",
    givenName = firstName ?: "",
    familyName = lastName ?: "",
    address = attributes[UserBase::address.name]?.first()?.parseJsonTo(AddressBase::class.java).orEmpty(),
    phone = attributes[UserBase::phone.name]?.firstOrNull(),
    roles = realmRoles,
    sendEmailLink = attributes[UserBase::sendEmailLink.name]?.firstOrNull().toBoolean()
)
