package i2.s2.client.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.cqrs.base.PageBase
import f2.dsl.cqrs.base.PageRequestBase
import f2.dsl.function.F2Function
import f2.dsl.function.F2FunctionRemote
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.s2.client.domain.ClientModel
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientGetPageQueryFunction = F2Function<ClientGetPageQuery, ClientGetPageQueryResult>
typealias ClientGetPageQueryRemoteFunction = F2FunctionRemote<ClientGetPageQuery, ClientGetPageQueryResult>

@JsExport
@JsName("ClientGetPageQuery")
class ClientGetPageQuery(
	val realmId: RealmId,
	val page: PageRequestBase,
	val auth: AuthRealm,
): Command

@JsExport
@JsName("ClientGetPageQueryResult")
class ClientGetPageQueryResult(
	val page: PageBase<ClientModel>
): Event