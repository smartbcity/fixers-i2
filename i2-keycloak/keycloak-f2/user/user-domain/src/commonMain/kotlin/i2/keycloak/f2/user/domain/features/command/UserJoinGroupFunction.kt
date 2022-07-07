package i2.keycloak.f2.user.domain.features.command

import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.commons.domain.KeycloakF2Result
import i2.keycloak.f2.group.domain.model.GroupId
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserJoinGroupFunction = F2Function<UserJoinGroupCommand, UserJoinedGroupEvent>

@JsExport
@JsName("UserJoinGroupCommand")
class UserJoinGroupCommand(
    val id: UserId,
    val groupId: GroupId,
    val leaveOtherGroups: Boolean? = false,
    val realmId: RealmId,
    override val auth: AuthRealm,
): KeycloakF2Command

@JsExport
@JsName("UserJoinedGroupEvent")
class UserJoinedGroupEvent(
	val id: UserId,
	val groupId: GroupId,
	val groupsLeft: List<GroupId>
): KeycloakF2Result
