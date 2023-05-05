package i2.config.api.config.keycloak

import i2.config.api.auth.KeycloakAggregateService
import i2.config.api.auth.KeycloakFinderService
import i2.keycloak.f2.client.domain.ClientId
import i2.keycloak.f2.role.domain.RoleName
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

const val SUPER_ADMIN_ROLE = "super_admin"
const val ORGANIZATION_ID_CLAIM_NAME = "memberOf"

@Service
class KeycloakConfig(
    private val keycloakAggregateService: KeycloakAggregateService,
    private val keycloakFinderService: KeycloakFinderService,
    private val keycloakConfigResolver: KeycloakConfigResolver
) {
    private val logger = LoggerFactory.getLogger(KeycloakConfig::class.java)

    fun run() {
        val config = keycloakConfigResolver.getConfiguration()
        run(config)
    }
    fun run(config: KeycloakConfigProperties) = runBlocking {
        try {
            logger.info("Initializing Roles...")
            initRoles(config.roles, config.roleComposites)
            logger.info("Initialized Roles")

            logger.info("Initializing Clients...")
            config.webClients.forEach { initWebClient(it) }
            config.appClients.forEach { initAppClient(it) }
            logger.info("Initialized Client")

            logger.info("Initializing Users...")
            initUsers(config.users)
            logger.info("Initialized Users")
        } catch (e: Exception) {
            logger.error("Error initializing keycloak", e)
        }
    }

    private suspend fun checkIfExists(clientId: ClientId): Boolean {
        return if (keycloakFinderService.getClient(clientId) != null) {
            logger.info("Client [$clientId] already exists.")
            true
        } else {
            false
        }
    }

    private suspend fun initAppClient(appClient: AppClient) {
        if (!checkIfExists(appClient.clientId)) {
            val secret = appClient.clientSecret ?: UUID.randomUUID().toString()
            keycloakAggregateService.createClient(
                identifier = appClient.clientId,
                secret = secret,
                isPublic = false,
                isServiceAccountsEnabled = true,
                isDirectAccessGrantsEnabled = false,
                isStandardFlowEnabled = false
            ).let {
                appClient.roles?.toList()?.let { list ->
                    keycloakAggregateService.grantClient(
                        id = appClient.clientId,
                        roles = list
                    )
                }
                appClient.realmManagementRoles?.toList()?.let { list ->
                    keycloakAggregateService.grantRealmManagementClient(
                        id = appClient.clientId,
                        roles = list,
                    )
                }
            }
            logger.info("App secret: $secret")
        }
    }

    private suspend fun initWebClient(webClient: WebClient) {
        if (!checkIfExists(webClient.clientId)) {
            keycloakAggregateService.createClient(
                identifier = webClient.clientId,
                baseUrl = webClient.webUrl,
                isStandardFlowEnabled = true,
                isDirectAccessGrantsEnabled = false,
                isServiceAccountsEnabled = false,
                isPublic = true,
                protocolMappers = mapOf(ORGANIZATION_ID_CLAIM_NAME to ORGANIZATION_ID_CLAIM_NAME),
            )
        }
    }

    private suspend fun initRoles(roles: List<RoleName>?, roleComposites: Map<RoleName, List<RoleName>>?) {
        roles?.let {
            roles.forEach { role ->
                initRoleWithComposites(role)
            }
            addCompositesToAdmin(roles.filter { it != SUPER_ADMIN_ROLE })
        }

        roleComposites?.let {
            roleComposites.forEach { roleComposite ->
                initRoleWithComposites(roleComposite.key, roleComposite.value)
            }
        }
    }

    private suspend fun initRoleWithComposites(role: RoleName, composites: List<RoleName> = emptyList()) {
        keycloakFinderService.getRole(role)
            ?: keycloakAggregateService.createRole(role)

        if (composites.isNotEmpty()) {
            keycloakAggregateService.addRoleComposites(role, composites)
        }
    }

    private suspend fun addCompositesToAdmin(composites: List<RoleName>) {
        keycloakAggregateService.addRoleComposites(SUPER_ADMIN_ROLE, composites)
    }

    private suspend fun initUsers(users: List<KeycloakUserConfig>?) {
        users?.let {
            users.forEach { initUser(it) }
        }
    }

    private suspend fun initUser(user: KeycloakUserConfig) {
        keycloakFinderService.getUser(user.email)
            ?: keycloakAggregateService.createUser(
                username = user.username,
                email = user.email,
                firstname = user.firstname,
                lastname = user.lastname,
                isEnable = true,
                password = user.password
            ).let { userId ->
                keycloakAggregateService.grantUser(userId, user.role)
            }
    }
}
