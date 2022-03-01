package i2.f2.import.domain.command

import f2.dsl.fnc.F2Function
import i2.f2.import.domain.RealmImport
import i2.keycloak.f2.client.domain.ClientId
import i2.keycloak.f2.client.domain.ClientIdentifier
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.commons.domain.KeycloakF2Result
import i2.keycloak.f2.role.domain.RoleId
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ImportDataFunction = F2Function<ImportDataCommand, ImportDataResult>

@JsExport
@JsName("ImportDataCommand")
class ImportDataCommand(
    val realmImport: RealmImport,
    override val auth: AuthRealm
): KeycloakF2Command

@JsExport
@JsName("ImportDataResult")
class ImportDataResult(
    val realmId: RealmId,
    val roleIds: List<RoleId>,
    val clientIdPerIdentifiers: Map<ClientId, ClientIdentifier>,
    val userIdPerUsernames: Map<UserId, String>
): KeycloakF2Result
