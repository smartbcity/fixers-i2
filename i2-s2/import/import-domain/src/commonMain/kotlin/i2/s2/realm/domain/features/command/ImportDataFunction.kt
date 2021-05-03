package i2.s2.realm.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.function.F2Function
import f2.dsl.function.F2FunctionRemote
import i2.keycloak.master.domain.AuthRealm
import i2.s2.realm.domain.RealmImport
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ImportDataFunction = F2Function<ImportDataCommand, ImportDataCommandResult>
typealias ImportDataRemoteFunction = F2FunctionRemote<ImportDataCommand, ImportDataCommandResult>

@JsExport
@JsName("ImportDataCommand")
class ImportDataCommand(
	val realmImport: RealmImport,
	val authRealm: AuthRealm
): Command

@JsExport
@JsName("ImportDataCommandResult")
class ImportDataCommandResult(
): Event