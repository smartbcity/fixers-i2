package i2.config.api.config.keycloak

import i2.config.api.auth.KeycloakAggregateService
import i2.config.api.auth.KeycloakFinderService
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger
import java.util.UUID

@Service
class KeycloakConfig(
    private val keycloakAggregateService: KeycloakAggregateService,
    private val keycloakFinderService: KeycloakFinderService,
    private val keycloakConfigResolver: KeycloakConfigResolver
) {
    private val logger by Logger()


    fun run() = runBlocking {
        try {
            val config = keycloakConfigResolver.getConfiguration()
            logger.info("Initializing Clients...")
            initApp(config.appClient)
            initWeb(config.webClient)
            logger.info("Initialized Client")

            logger.info("Initializing Roles...")
            initRoles(config.roles, config.roleComposites)
            logger.info("Initialized Roles")

            logger.info("Initializing Users...")
            initUsers(config.users)
            logger.info("Initialized Users")
        } catch (e: Exception) {
            logger.error("Error initializing keycloak", e)
        }
    }

    private suspend fun initApp(appClient: AppClient) = createClientIfNotExists(appClient.clientId) { clientId ->
        val secret = UUID.randomUUID().toString()
        keycloakAggregateService.createClient(
            identifier = clientId,
            secret = secret,
            isPublic = false
        )
        logger.info("App secret: $secret")
    }

    private suspend fun initWeb(webClient: WebClient) = createClientIfNotExists(webClient.clientId) { clientId ->
        keycloakAggregateService.createClient(
            identifier = clientId,
            baseUrl = webClient.webUrl,
            localhostUrl = webClient.localhostUrl,
            isStandardFlowEnabled = true
        )
    }

    private suspend fun createClientIfNotExists(clientId: String, createClient: suspend (id: String) -> Unit) {
        keycloakFinderService.getClient(clientId) ?: createClient(clientId)
    }

    private suspend fun initRoles(roles: List<String>?, roleComposites: Map<String, List<String>>?) {
        roles?.let {
            roles.forEach { role ->
                initRoleWithComposites(role)
            }
        }

        roleComposites?.let {
            roleComposites.forEach { roleComposite ->
                initRoleWithComposites(roleComposite.key, roleComposite.value)
            }
        }
    }

    private suspend fun initRoleWithComposites(role: String, composites: List<String> = emptyList()) {
        keycloakFinderService.getRole(role)
            ?: keycloakAggregateService.createRole(role)

        if (composites.isNotEmpty()) {
            keycloakAggregateService.addRoleComposites(role, composites)
        }
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
