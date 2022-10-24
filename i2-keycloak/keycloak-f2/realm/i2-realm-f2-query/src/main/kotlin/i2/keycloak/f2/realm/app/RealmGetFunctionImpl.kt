package i2.keycloak.f2.realm.app

import f2.dsl.fnc.f2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.realm.domain.RealmModel
import i2.keycloak.f2.realm.domain.features.query.RealmGetFunction
import i2.keycloak.f2.realm.domain.features.query.RealmGetResult
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import javax.ws.rs.NotFoundException
import org.keycloak.representations.idm.RealmRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger

@Configuration
class RealmGetFunctionImpl {

	private val logger by Logger()

	@Bean
	fun realmGetFunction(): RealmGetFunction = f2Function { cmd ->
		try {
			val masterRealm = AuthRealmClientBuilder().build(cmd.authRealm)
			val model = masterRealm.keycloak.realm(cmd.id).toRepresentation().asRealmModel()
			RealmGetResult(model)
		} catch (e: NotFoundException) {
			RealmGetResult(null)
		} catch (e: Exception) {
			val msg = "Error fetching realm with id[${cmd.id}]"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception()
		}
	}

	private fun RealmRepresentation.asRealmModel(): RealmModel {
		return RealmModel(
			id = this.id,
			name = this.displayName,
		)
	}
}