package i2.f2.organization.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.commons.model.AddressBase
import i2.f2.organization.domain.model.OrganizationId

typealias OrganizationCreateFunction = F2Function<OrganizationCreateCommand, OrganizationCreatedResult>

data class OrganizationCreateCommand(
    val siret: String?,
    val name: String,
    val description: String?,
    val address: AddressBase?,
    val website: String?,
    val roles: List<String>?,
    val parentOrganizationId: OrganizationId
): Command

data class OrganizationCreatedResult(
    val id: OrganizationId,
    val parentOrganization: OrganizationId
): Event
