package i2.s2.role.domain.features.query

import f2.dsl.cqrs.Page
import f2.dsl.cqrs.base.PageRequestBase
import f2.dsl.function.F2Function
import f2.dsl.function.F2FunctionRemote
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.s2.role.domain.RoleModel
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RoleGetPageQueryFunction = F2Function<RoleGetPageQuery, RoleGetPageQueryResult>
typealias RoleGetPageQueryFunctionRemote = F2FunctionRemote<RoleGetPageQuery, RoleGetPageQueryResult>

@JsExport
@JsName("RoleGetPageQuery")
class RoleGetPageQuery(
    val realmId: RealmId,
    val auth: AuthRealm,
    val page: PageRequestBase,
)

@JsExport
@JsName("RoleGetPageQueryResult")
class RoleGetPageQueryResult(
    val page: Page<RoleModel>
)