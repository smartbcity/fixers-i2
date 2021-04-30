package i2.s2.client.f2

import f2.function.spring.adapter.f2Function
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.s2.client.domain.ClientModel
import i2.s2.client.domain.features.query.ClientGetByIdQueryFunction
import i2.s2.client.domain.features.query.ClientGetByIdQueryResult
import i2.s2.errors.I2ApiError
import i2.s2.errors.asI2Exception
import org.keycloak.representations.idm.ClientRepresentation
import org.springframework.context.annotation.Bean
import s2.spring.utils.logger.Logger
import javax.ws.rs.NotFoundException

class ClientGetByIdQueryFunctionImpl {
    private val logger by Logger()

    @Bean
    fun clientGetByIdQueryFunction(): ClientGetByIdQueryFunction = f2Function { cmd ->
        val realmClient = AuthRealmClientBuilder().build(cmd.auth)
        try {
            realmClient.getClientResource(cmd.realmId, cmd.id)
                .toRepresentation()
                .asModel()
                .asResult()
        } catch (e: NotFoundException) {
            ClientGetByIdQueryResult(null)
        } catch (e: Exception) {
            val msg = "Error fetching client with id[${cmd.id}]"
            logger.error(msg, e)
            throw I2ApiError(
                description = msg,
                payload = emptyMap()
            ).asI2Exception()
        }
    }

    private fun ClientRepresentation.asModel(): ClientModel {
        return ClientModel(
            id = this.id
        )
    }

    private fun ClientModel.asResult(): ClientGetByIdQueryResult {
        return ClientGetByIdQueryResult(this)
    }
}