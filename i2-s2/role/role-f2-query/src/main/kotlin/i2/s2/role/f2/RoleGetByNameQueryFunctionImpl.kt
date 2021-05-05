package i2.s2.role.f2

import f2.function.spring.adapter.f2Function
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.s2.errors.I2ApiError
import i2.s2.errors.asI2Exception
import i2.s2.role.domain.features.query.RoleGetByNameQueryFunction
import i2.s2.role.domain.features.query.RoleGetByNameQueryResult
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger
import javax.ws.rs.NotFoundException

@Configuration
class RoleGetByNameQueryFunctionImpl {
    private val logger by Logger()

    @Bean
    fun roleGetByNameQueryFunction(): RoleGetByNameQueryFunction = f2Function { cmd ->
        val realmClient = AuthRealmClientBuilder().build(cmd.auth)
        try {
            realmClient.getRoleResource(cmd.realmId, cmd.name)
                .toRepresentation()
                .asModel()
                .let(::RoleGetByNameQueryResult)
        } catch (e: NotFoundException) {
            RoleGetByNameQueryResult(null)
        } catch (e: Exception) {
            val msg = "Error fetching role with name[${cmd.name}]"
            logger.error(msg, e)
            throw I2ApiError(
                description = msg,
                payload = emptyMap()
            ).asI2Exception()
        }
    }
}