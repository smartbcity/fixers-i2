package i2.s2.organization.f2.model

import i2.s2.commons.f2.utils.parseJsonTo
import i2.s2.group.domain.model.GroupModel
import i2.s2.organization.domain.model.AddressBase
import i2.s2.organization.domain.model.Organization
import i2.s2.organization.domain.model.OrganizationBase

fun GroupModel.toOrganization() = OrganizationBase(
    id = id,
    name = name,
    siret = attributes[Organization::siret.name]!!.first(),
    address = attributes[Organization::address.name]!!.first().parseJsonTo(AddressBase::class.java),
    description = attributes[Organization::description.name]?.firstOrNull()
)
