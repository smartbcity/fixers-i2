package i2.init.api.init.keycloak

import kotlinx.coroutines.runBlocking
import i2.init.api.auth.KeycloakAggregateService
import i2.init.api.auth.KeycloakFinderService
import i2.init.api.auth.config.KeycloakConfig
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger
import java.util.UUID


@Service
class KeycloakInit(
    private val keycloakAggregateService: KeycloakAggregateService,
    private val keycloakConfig: KeycloakConfig,
    private val keycloakInitConfig: KeycloakInitConfig,
    private val keycloakFinderService: KeycloakFinderService
) {
    private val logger by Logger()

    fun init() = runBlocking {
        try {
            logger.info("Initializing Realm...")
            initRealm()
            logger.info("Initialized Realm")

            logger.info("Initializing Clients...")
            initClients()
            logger.info("Initialized Client")

            logger.info("Initializing Roles...")
            initRoles()
            logger.info("Initialized Roles")

            logger.info("Initializing Users...")
            initUsers()
            logger.info("Initialized Users")
        } catch (e: Exception) {
            logger.error("Error initializing keycloak", e)
        }
    }

    private suspend fun initRealm() {
        val realmId = keycloakConfig.realm
        keycloakFinderService.getRealm(realmId)
            ?: keycloakAggregateService.createRealm(realmId, keycloakInitConfig.smtpConfig())
    }

    private suspend fun initClients() {
        initApp()
        initWeb()
    }

    private suspend fun initApp() = createClientIfNotExists(keycloakConfig.appClientId) { clientId ->
        val secret = UUID.randomUUID().toString()
        keycloakAggregateService.createClient(
            identifier = clientId,
            secret = secret,
            isPublic = false
        )
        logger.info("App secret: $secret")
    }

    private suspend fun initWeb() = createClientIfNotExists(keycloakConfig.webClientId) { clientId ->
        keycloakAggregateService.createClient(
            identifier = clientId,
            baseUrl = keycloakInitConfig.webUrl,
            localhostUrl = keycloakInitConfig.localhostUrl,
            isStandardFlowEnabled = true
        )
    }

    private suspend fun createClientIfNotExists(clientId: String, createClient: suspend (id: String) -> Unit) {
        keycloakFinderService.getClient(clientId) ?: createClient(clientId)
    }

    private suspend fun initRoles() {
        keycloakInitConfig.roles().forEach { role ->
            initRoleWithComposites(role)
        }
        keycloakInitConfig.roleComposites().forEach { roleComposite ->
            initRoleWithComposites(roleComposite.key, roleComposite.value)
        }
    }

    private suspend fun initRoleWithComposites(role: String, composites: List<String> = emptyList()) {
        keycloakFinderService.getRole(role)
            ?: keycloakAggregateService.createRole(role)

        if (composites.isNotEmpty()) {
            keycloakAggregateService.addRoleComposites(role, composites)
        }
    }

    private suspend fun initUsers() {
        keycloakInitConfig.users().forEach { initUser(it) }
    }

    private suspend fun initUser(user: KeycloakUserConfig) {
        keycloakFinderService.getUser(user.email)
            ?: keycloakAggregateService.createUser(
                username = user.username,
                email = user.email,
                firstname = user.firstname,
                lastname = user.lastname,
                isEnable = true
            ).let { userId ->
                keycloakAggregateService.grantUser(userId, user.role)
            }
    }
}
