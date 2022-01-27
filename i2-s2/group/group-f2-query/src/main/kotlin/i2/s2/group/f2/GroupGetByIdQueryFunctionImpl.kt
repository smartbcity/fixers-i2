package i2.s2.group.f2

import i2.s2.commons.f2.keycloakF2Function
import i2.s2.errors.I2ApiError
import i2.s2.errors.asI2Exception
import i2.s2.group.domain.features.query.GroupGetByIdQueryFunction
import i2.s2.group.domain.features.query.GroupGetByIdQueryResult
import i2.s2.group.f2.model.asModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger
import javax.ws.rs.NotFoundException

@Configuration
class GroupGetByIdQueryFunctionImpl {

	private val logger by Logger()

	@Bean
	fun groupGetByIdQueryFunction(): GroupGetByIdQueryFunction = keycloakF2Function { cmd, client ->
		try {
			client.getGroupResource(cmd.realmId, cmd.id)
				.toRepresentation()
				.asModel()
				.let(::GroupGetByIdQueryResult)
		} catch (e: NotFoundException) {
			GroupGetByIdQueryResult(null)
		} catch (e: Exception) {
			val msg = "Error fetching Group with id[${cmd.id}]"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception()
		}
	}
}
