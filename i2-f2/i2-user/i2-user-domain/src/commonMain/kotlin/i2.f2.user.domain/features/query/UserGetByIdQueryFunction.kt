package i2.f2.user.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.f2.user.domain.model.UserBase
import i2.f2.user.domain.model.UserId

typealias UserGetByIdQueryFunction = F2Function<UserGetByIdQuery, UserGetByIdQueryResult>

class UserGetByIdQuery(
    val id: UserId
): Command

class UserGetByIdQueryResult(
	val user: UserBase?
): Event
