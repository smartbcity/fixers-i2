package i2.s2.realm.domain.features.command

import f2.dsl.function.F2Function
import f2.dsl.function.F2FunctionRemote
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.domain.UserId
import i2.s2.client.domain.ClientId
import i2.s2.client.domain.ClientIdentifier
import i2.s2.commons.f2.KeycloakF2Command
import i2.s2.commons.f2.KeycloakF2Result
import i2.s2.realm.domain.RealmImport
import i2.s2.role.domain.RoleId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ImportDataFunction = F2Function<ImportDataCommand, ImportDataResult>
typealias ImportDataRemoteFunction = F2FunctionRemote<ImportDataCommand, ImportDataResult>

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