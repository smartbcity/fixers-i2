package i2.f2.user.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.f2.user.domain.model.UserId

/**
 * Sets a new password for the user.
 * @d2 section
 * @parent [i2.f2.user.domain.D2UserCommandSection]
 */
typealias UserResetPasswordFunction = F2Function<UserResetPasswordCommand, UserResetPasswordResult>

/**
 * @d2 command
 * @parent [UserResetPasswordFunction]
 */
data class UserResetPasswordCommand(
    /**
     * Identifier of the user.
     */
    val id: UserId,
    /**
     * New password of the user.
     */
    val password: String
): Command

/**
 * @d2 event
 * @parent [UserResetPasswordFunction]
 */
data class UserResetPasswordResult(
    /**
     * Identifier of the user.
     */
    val id: UserId
): Event
