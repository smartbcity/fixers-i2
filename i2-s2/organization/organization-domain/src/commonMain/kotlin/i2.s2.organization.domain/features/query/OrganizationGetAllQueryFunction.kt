package i2.s2.organization.domain.features.query

import f2.dsl.fnc.F2Function
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.s2.commons.f2.KeycloakF2Command
import i2.s2.commons.f2.KeycloakF2Result
import i2.s2.organization.domain.model.OrganizationBase

typealias OrganizationGetAllQueryFunction = F2Function<OrganizationGetAllQuery, OrganizationGetAllQueryResult>

class OrganizationGetAllQuery(
	val search: String?,
	val page: Int,
	val size: Int,
	val realmId: RealmId,
	override val auth: AuthRealm,
): KeycloakF2Command

class OrganizationGetAllQueryResult(
	val organizations: List<OrganizationBase>
): KeycloakF2Result
