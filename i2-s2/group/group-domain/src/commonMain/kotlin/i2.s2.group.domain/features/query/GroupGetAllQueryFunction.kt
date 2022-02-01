package i2.s2.group.domain.features.query

import f2.dsl.fnc.F2Function
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.s2.commons.f2.KeycloakF2Command
import i2.s2.commons.f2.KeycloakF2Result
import i2.s2.group.domain.model.GroupModel

typealias GroupGetAllQueryFunction = F2Function<GroupGetAllQuery, GroupGetAllQueryResult>

class GroupGetAllQuery(
	val search: String,
	val page: Int,
	val size: Int,
	val realmId: RealmId,
	override val auth: AuthRealm,
): KeycloakF2Command

class GroupGetAllQueryResult(
	val groups: List<GroupModel>
): KeycloakF2Result
