package i2.s2.commons.f2

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.master.domain.AuthRealm
import kotlin.js.JsExport
import kotlin.js.JsName

typealias KeycloakF2Function = F2Function<KeycloakF2Command, KeycloakF2Result>

//@JsExport
@JsName("KeycloakF2Command")
interface KeycloakF2Command: Command {
    val auth: AuthRealm
}

//@JsExport
@JsName("KeycloakF2Result")
interface KeycloakF2Result: Event
