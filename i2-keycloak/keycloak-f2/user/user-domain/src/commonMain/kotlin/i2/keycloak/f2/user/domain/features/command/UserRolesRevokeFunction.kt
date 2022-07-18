package i2.keycloak.f2.user.domain.features.command

import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserRolesRevokeFunction = F2Function<UserRolesRevokeCommand, UserRolesRevokedEvent>

@JsExport
@JsName("UserRolesRevokeCommand")
class UserRolesRevokeCommand(
	val id: UserId,
	val roles: List<String>,
	override val auth: AuthRealm,
	val realmId: RealmId = auth.realmId
): KeycloakF2Command

@JsExport
@JsName("UserRolesRevokedEvent")
class UserRolesRevokedEvent(
	val id: String
): Event
