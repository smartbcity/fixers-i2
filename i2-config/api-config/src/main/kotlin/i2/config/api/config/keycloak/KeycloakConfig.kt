package i2.config.api.config.keycloak

import i2.config.api.auth.KeycloakAggregateService
import i2.config.api.auth.KeycloakFinderService
import i2.keycloak.f2.client.domain.ClientIdentifier
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
            initApp(config.appClient)
            initWeb(config.webClient)
            logger.info("Initialized Client")

            logger.info("Initializing Users...")
            initUsers(config.users)
            logger.info("Initialized Users")
        } catch (e: Exception) {
            logger.error("Error initializing keycloak", e)
        }
    }

    private suspend fun initApp(appClient: AppClient) = appClient.createClientIfNotExists { clientId ->
        val secret = appClient.secret ?: UUID.randomUUID().toString()
        keycloakAggregateService.createClient(
            identifier = clientId,
            secret = secret,
            isPublic = false
        )
        logger.info("App secret: $secret")
    }

    private suspend fun initWeb(webClient: WebClient) = webClient.createClientIfNotExists { clientId ->
        val secret = webClient.secret ?: UUID.randomUUID().toString()
        keycloakAggregateService.createClient(
            identifier = clientId,
            secret = secret,
            baseUrl = webClient.webUrl,
            localhostUrl = webClient.localhostUrl,
            isStandardFlowEnabled = true,
            protocolMappers = mapOf(ORGANIZATION_ID_CLAIM_NAME to ORGANIZATION_ID_CLAIM_NAME),
        )
    }

    private suspend fun AppClient.createClientIfNotExists(
        createClient: suspend (id: ClientIdentifier) -> Unit
    ) {
        keycloakFinderService.getClient(clientId) ?: createClient(clientId).let {
            roles?.toList()?.let { list ->
                keycloakAggregateService.grantClient(
                    id = clientId,
                    roles = list
                )
            }
            realmManagementRoles?.toList()?.let { list ->
                keycloakAggregateService.grantRealmManagementClient(
                    id = clientId,
                    roles = list,
                )
            }
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
