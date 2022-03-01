package i2.f2.user.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.f2.organization.domain.model.OrganizationId
import i2.f2.user.domain.model.UserBase

typealias UserGetAllQueryFunction = F2Function<UserGetAllQuery, UserGetAllQueryResult>

class UserGetAllQuery(
	val organizationId: OrganizationId?,
	val page: Int,
	val size: Int,
): Command

class UserGetAllQueryResult(
	val users: List<UserBase>,
	val total: Int
): Event
