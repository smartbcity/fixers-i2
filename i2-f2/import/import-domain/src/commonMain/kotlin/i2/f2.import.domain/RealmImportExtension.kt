package i2.f2.import.domain

import i2.keycloak.f2.client.domain.features.command.ClientCreateCommand
import i2.keycloak.f2.realm.domain.RealmId
import i2.keycloak.f2.realm.domain.features.command.RealmCreateCommand
import i2.keycloak.f2.realm.domain.features.command.UserCreateCommand
import i2.keycloak.f2.role.domain.features.command.RoleAddCompositesCommand
import i2.keycloak.f2.role.domain.features.command.RoleCreateCommand
import i2.keycloak.master.domain.AuthRealm

fun RealmImport.toRealmCreateCommand(auth: AuthRealm) = RealmCreateCommand(
    id = this.id,
    theme = this.theme,
    locale = this.locale,
    smtpServer = this.smtpServer,
    masterRealmAuth = auth
)

fun RoleImport.toRoleCreateCommandWithoutComposites(auth: AuthRealm, realmId: RealmId) = RoleCreateCommand(
    name = this.name,
    description = this.description,
    isClientRole = this.isClientRole,
    composites = emptyList(),
    auth = auth,
    realmId = realmId
)

fun RoleImport.toRoleAddCompositesCommand(auth: AuthRealm, realmId: RealmId) = RoleAddCompositesCommand(
    roleName = this.name,
    composites = this.composites,
    auth = auth,
    realmId = realmId
)

fun ClientImport.toClientCreateCommand(auth: AuthRealm, realmId: RealmId) = ClientCreateCommand(
    clientIdentifier = this.clientIdentifier,
    auth = auth,
    realmId = realmId,
    isPublicClient = this.isPublicClient,
    isDirectAccessGrantsEnabled = this.isDirectAccessGrantsEnabled,
    isServiceAccountsEnabled = this.isServiceAccountsEnabled,
    authorizationServicesEnabled = this.authorizationServicesEnabled,
    rootUrl = this.rootUrl,
    redirectUris = this.redirectUris,
    baseUrl = this.baseUrl,
    adminUrl = this.adminUrl,
    protocolMappers = this.protocolMappers,
    webOrigins = this.webOrigins
)

fun UserImport.toUserCreateCommand(auth: AuthRealm, realmId: RealmId) = UserCreateCommand(
    auth = auth,
    realmId = realmId,
    username = this.username,
    firstname = this.firstname,
    lastname = this.lastname,
    email = this.email,
    isEnable = this.isEnable,
    metadata = this.metadata
)
