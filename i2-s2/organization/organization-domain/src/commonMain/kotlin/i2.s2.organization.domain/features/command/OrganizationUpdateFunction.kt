package i2.s2.organization.domain.features.command

import f2.dsl.fnc.F2Function
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.s2.commons.f2.KeycloakF2Command
import i2.s2.commons.f2.KeycloakF2Result
import i2.s2.organization.domain.model.AddressBase
import i2.s2.organization.domain.model.OrganizationId

typealias OrganizationUpdateFunction = F2Function<OrganizationUpdateCommand, OrganizationUpdatedResult>

data class OrganizationUpdateCommand(
    val id: OrganizationId,
    val siret: String,
    val name: String,
    val description: String?,
    val address: AddressBase,
    override val auth: AuthRealm,
    val realmId: RealmId
): KeycloakF2Command

data class OrganizationUpdatedResult(
    val id: OrganizationId
): KeycloakF2Result
