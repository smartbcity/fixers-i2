package i2.keycloak.realm.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.cqrs.base.PageBase
import f2.dsl.cqrs.base.PageRequestBase
import f2.dsl.fnc.F2Function
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.domain.UserModel
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserGetPageQueryFunction = F2Function<UserGetPageQuery, UserGetPageQueryResult>

@JsExport
@JsName("UserGetPageQuery")
class UserGetPageQuery(
	val realmId: RealmId,
	val page: PageRequestBase,
	val auth: AuthRealm,
) : Command

@JsExport
@JsName("UserGetPageQueryResult")
class UserGetPageQueryResult(
	val page: PageBase<UserModel>
) : Event