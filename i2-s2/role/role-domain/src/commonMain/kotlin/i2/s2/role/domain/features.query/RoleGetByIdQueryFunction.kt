package i2.s2.role.domain.features.query

import f2.dsl.fnc.F2Function
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.s2.role.domain.RoleModel
import i2.s2.role.domain.RoleName
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RoleGetByNameQueryFunction = F2Function<RoleGetByNameQuery, RoleGetByNameQueryResult>

@JsExport
@JsName("RoleGetByNameQuery")
class RoleGetByNameQuery(
    val realmId: RealmId,
    val auth: AuthRealm,
    val name: RoleName
)

@JsExport
@JsName("RoleGetByNameQueryResult")
class RoleGetByNameQueryResult(
    val role: RoleModel?
)