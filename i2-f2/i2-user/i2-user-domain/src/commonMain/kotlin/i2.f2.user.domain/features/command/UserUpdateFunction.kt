package i2.f2.user.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.commons.model.AddressBase
import i2.f2.user.domain.model.UserId

typealias UserUpdateFunction = F2Function<UserUpdateCommand, UserUpdatedResult>

data class UserUpdateCommand(
    val id: UserId,
    val email: String,
    val givenName: String,
    val familyName: String,
    val address: AddressBase,
    val phone: String?,
    val sendEmailLink: Boolean
): Command

data class UserUpdatedResult(
    val id: UserId
): Event
