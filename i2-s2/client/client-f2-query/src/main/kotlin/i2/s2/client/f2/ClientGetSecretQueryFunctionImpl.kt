package i2.s2.client.f2

import f2.function.spring.adapter.f2Function
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.s2.client.domain.features.query.ClientGetSecretQueryFunction
import i2.s2.client.domain.features.query.ClientGetSecretQueryResult
import i2.s2.errors.I2ApiError
import i2.s2.errors.asI2Exception
import org.keycloak.representations.idm.CredentialRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger

@Configuration
class ClientGetSecretQueryFunctionImpl {
    private val logger by Logger()

    @Bean
    fun clientGetSecretQueryFunction(): ClientGetSecretQueryFunction = f2Function { cmd ->
        val realmClient = AuthRealmClientBuilder().build(cmd.auth)
        try {
            realmClient.getClientResource(cmd.realmId, cmd.clientId)
                .secret
                .asResult()
        } catch (e: Exception) {
            val msg = "Error fetching secret of client with id[${cmd.clientId}]"
            logger.error(msg, e)
            throw I2ApiError(
                description = msg,
                payload = emptyMap()
            ).asI2Exception()
        }
    }

    private fun CredentialRepresentation.asResult() = ClientGetSecretQueryResult(value)
}