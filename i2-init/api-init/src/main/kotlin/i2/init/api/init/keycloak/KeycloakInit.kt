package i2.init.api.init.keycloak

import i2.init.api.auth.KeycloakAggregateService
import i2.init.api.auth.KeycloakFinderService
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger

const val SUPER_ADMIN_ROLE = "super_admin"

@Service
class KeycloakInit(
    private val keycloakAggregateService: KeycloakAggregateService,
    private val keycloakInitConfig: KeycloakInitConfig,
    private val keycloakFinderService: KeycloakFinderService,
) {
    private val logger by Logger()

    fun init() = runBlocking {
        try {
            logger.info("Initializing Realm [${keycloakInitConfig.realm}]...")
            initRealm()
            logger.info("Initialized Realm")

            logger.info("Initializing Client [${keycloakInitConfig.clientId}]...")
            initAdminClient()
            logger.info("Initialized Client")

            logger.info("Initializing Base roles...")
            initBaseRoles()
            logger.info("Initialized Base roles")

            logger.info("Adding composite roles for Admin...")
            addCompositesToAdminRole()
            logger.info("Added composite roles for Admin")

            logger.info("Initializing Admin user [${keycloakInitConfig.username}]...")
            initAdmin()
            logger.info("Initialized Admin")

        } catch (e: Exception) {
            logger.error("Error initializing keycloak", e)
        }
    }

    private suspend fun initRealm() {
        val realmId = keycloakInitConfig.realm
        keycloakFinderService.getRealm(realmId)?.let {
          logger.info("Realm already created")
        } ?: keycloakAggregateService.createRealm(realmId, keycloakInitConfig.smtpConfig())
    }

    private suspend fun initAdminClient() = createClientIfNotExists(keycloakInitConfig.clientId) { clientId ->
        val secret = keycloakInitConfig.clientSecret ?: UUID.randomUUID().toString()
        logger.info("Creating admin client with secret: $secret")
        keycloakAggregateService.createClient(
            identifier = clientId,
            secret = secret,
            isPublic = false,
            realm = keycloakInitConfig.realm
        ).let {
            keycloakAggregateService.grantClient(
                id = keycloakInitConfig.clientId,
                rolesProviderClientId = "realm-management",
                realm = keycloakInitConfig.realm,
                roles = listOf<String>(
                    "create-client",
                    "manage-clients",
                    "manage-users",
                    "manage-realm",
                    "view-clients",
                    "view-users"
                )
            )
        }
    }

    private suspend fun createClientIfNotExists(clientId: String, createClient: suspend (id: String) -> Unit) {
        keycloakFinderService.getClient(clientId, keycloakInitConfig.realm)?.let {
            logger.info("Client already created")
        } ?: createClient(clientId)
    }

    private suspend fun initAdmin() {
        if (keycloakFinderService.getUser(keycloakInitConfig.email, keycloakInitConfig.realm) != null) {
            logger.info("User admin already created")
        } else {
            val password = keycloakInitConfig.password ?: UUID.randomUUID().toString()
            logger.info("Creating user admin with password: $password")
            keycloakAggregateService.createUser(
                username = keycloakInitConfig.username,
                email = keycloakInitConfig.email,
                firstname = keycloakInitConfig.firstname,
                lastname = keycloakInitConfig.lastname,
                isEnable = true,
                password = password,
                realm = keycloakInitConfig.realm
            ).let { userId ->
                keycloakAggregateService.grantUser(
                    id = userId,
                    realm = keycloakInitConfig.realm,
                    clientId = "realm-management",
                    "realm-admin"
                )
                keycloakAggregateService.grantUser(
                    id = userId,
                    realm = keycloakInitConfig.realm,
                    clientId = null,
                    SUPER_ADMIN_ROLE
                )
            }
        }
    }

    private suspend fun initBaseRoles() {
        val roles = keycloakInitConfig.baseRoles()
        roles.forEach { role ->
            keycloakFinderService.getRole(role, keycloakInitConfig.realm)
                ?: keycloakAggregateService.createRole(role, "Role created with i2-init", emptyList(), keycloakInitConfig.realm)
        }
    }

    private suspend fun addCompositesToAdminRole() {
        val composites = keycloakInitConfig.baseRoles().filter { it != SUPER_ADMIN_ROLE }
        keycloakAggregateService.roleAddComposites(SUPER_ADMIN_ROLE, composites, keycloakInitConfig.realm)
    }
}
