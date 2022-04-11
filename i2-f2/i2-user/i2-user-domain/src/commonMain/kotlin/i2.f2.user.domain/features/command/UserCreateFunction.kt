package i2.f2.user.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.commons.model.AddressBase
import i2.f2.organization.domain.model.OrganizationId
import i2.f2.user.domain.model.UserId

typealias UserCreateFunction = F2Function<UserCreateCommand, UserCreatedResult>

data class UserCreateCommand(
    val email: String,
    val givenName: String,
    val familyName: String,
    val address: AddressBase?,
    val phone: String?,
    val roles: List<String>,
    val sendEmailLink: Boolean,
    val memberOf: OrganizationId?,
): Command

data class UserCreatedResult(
    val id: UserId
): Event
