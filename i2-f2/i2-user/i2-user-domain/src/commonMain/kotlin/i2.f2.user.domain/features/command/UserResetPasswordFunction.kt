package i2.f2.user.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.f2.user.domain.model.UserId

typealias UserResetPasswordFunction = F2Function<UserResetPasswordCommand, UserPasswordResetResult>

data class UserResetPasswordCommand(
    val id: UserId,
    val password: String
): Command

data class UserPasswordResetResult(
    val id: UserId
): Event
