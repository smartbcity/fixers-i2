package i2.s2.organization.domain.features.query

import f2.dsl.fnc.F2Function
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.s2.commons.f2.KeycloakF2Command
import i2.s2.commons.f2.KeycloakF2Result
import i2.s2.organization.domain.model.OrganizationBase
import i2.s2.organization.domain.model.OrganizationId

typealias OrganizationGetByIdQueryFunction = F2Function<OrganizationGetByIdQuery, OrganizationGetByIdQueryResult>

class OrganizationGetByIdQuery(
	val id: OrganizationId,
	val realmId: RealmId,
	override val auth: AuthRealm,
): KeycloakF2Command

class OrganizationGetByIdQueryResult(
	val group: OrganizationBase?
): KeycloakF2Result
