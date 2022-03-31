package i2.f2.organization.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.commons.model.AddressBase
import i2.f2.organization.domain.model.OrganizationId

typealias OrganizationUpdateFunction = F2Function<OrganizationUpdateCommand, OrganizationUpdatedResult>

data class OrganizationUpdateCommand(
    val id: OrganizationId,
    val name: String,
    val description: String?,
    val address: AddressBase?,
    val website: String?,
    val roles: List<String>?
): Command

data class OrganizationUpdatedResult(
    val id: OrganizationId
): Event
