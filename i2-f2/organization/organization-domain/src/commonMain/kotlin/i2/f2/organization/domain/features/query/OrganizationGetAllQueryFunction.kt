package i2.f2.organization.domain.features.query

import f2.dsl.fnc.F2Function
import i2.f2.organization.domain.model.OrganizationBase
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.commons.domain.KeycloakF2Result
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId

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
