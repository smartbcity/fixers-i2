package i2.init.api.init.keycloak

import kotlinx.coroutines.runBlocking
import i2.init.api.auth.KeycloakAggregateService
import i2.init.api.auth.KeycloakFinderService
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger
import java.util.UUID

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
        val secret = UUID.randomUUID().toString()
        logger.info("Creating admin client with secret: $secret")
        keycloakAggregateService.createClient(
            identifier = clientId,
            secret = secret,
            isPublic = false,
            realm = keycloakInitConfig.realm
        ).let {
            keycloakAggregateService.grantClient(
                id = keycloakInitConfig.clientId,
                rolesProviderClientId = keycloakInitConfig.clientRolesProviderClientId,
                realm = keycloakInitConfig.realm,
                roles = keycloakInitConfig.clientRoles()
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
            val password = UUID.randomUUID().toString()
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
                    clientId = keycloakInitConfig.roleClientId,
                    keycloakInitConfig.role
                )
            }
        }
    }
}
