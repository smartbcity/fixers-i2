package i2.s2.role.domain.features.query

import f2.dsl.function.F2Function
import f2.dsl.function.F2FunctionRemote
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.s2.role.domain.RoleId
import i2.s2.role.domain.RoleModel
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RoleGetByIdQueryFunction = F2Function<RoleGetByIdQuery, RoleGetByIdQueryResult>
typealias RoleGetByIdQueryFunctionRemote = F2FunctionRemote<RoleGetByIdQuery, RoleGetByIdQueryResult>

@JsExport
@JsName("RoleGetByIdQuery")
class RoleGetByIdQuery(
    val realmId: RealmId,
    val auth: AuthRealm,
    val id: RoleId
)

@JsExport
@JsName("RoleGetByIdQueryResult")
class RoleGetByIdQueryResult(
    val role: RoleModel?
)