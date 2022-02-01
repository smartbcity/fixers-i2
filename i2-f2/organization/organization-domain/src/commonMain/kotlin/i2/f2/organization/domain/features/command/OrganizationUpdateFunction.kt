package i2.f2.organization.domain.features.command

import f2.dsl.fnc.F2Function
import i2.f2.organization.domain.model.AddressBase
import i2.f2.organization.domain.model.OrganizationId
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.commons.domain.KeycloakF2Result
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId

typealias OrganizationUpdateFunction = F2Function<OrganizationUpdateCommand, OrganizationUpdatedResult>

data class OrganizationUpdateCommand(
    val id: OrganizationId,
    val siret: String,
    val name: String,
    val description: String?,
    val address: AddressBase,
    val website: String?,
    override val auth: AuthRealm,
    val realmId: RealmId
): KeycloakF2Command

data class OrganizationUpdatedResult(
    val id: OrganizationId
): KeycloakF2Result
