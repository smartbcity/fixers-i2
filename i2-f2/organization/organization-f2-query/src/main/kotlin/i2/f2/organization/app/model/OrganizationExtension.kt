package i2.f2.organization.app.model

import i2.commons.model.AddressBase
import i2.commons.utils.parseJsonTo
import i2.f2.organization.domain.model.Organization
import i2.f2.organization.domain.model.OrganizationBase
import i2.keycloak.f2.group.domain.model.GroupModel

fun GroupModel.toOrganization() = OrganizationBase(
    id = id,
    name = name,
    siret = attributes[Organization::siret.name]?.first() ?: "",
    address = attributes[Organization::address.name]?.first()?.parseJsonTo(AddressBase::class.java).orEmpty(),
    description = attributes[Organization::description.name]?.firstOrNull(),
    website = attributes[Organization::website.name]?.firstOrNull()
)
