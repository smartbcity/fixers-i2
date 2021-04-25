package i2.s2.user.f2

import f2.function.spring.adapter.f2Function
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.keycloak.realm.domain.UserModel
import i2.keycloak.realm.domain.features.query.UserGetOneQueryFunction
import i2.keycloak.realm.domain.features.query.UserGetOneQueryResult
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserGetPageQueryFunctionImpl {

	@Bean
	fun realmGetOneQueryFunction(): UserGetOneQueryFunction = f2Function { cmd ->
		val realmClient = AuthRealmClientBuilder().build(cmd.auth)
		realmClient.getUserResource(cmd.auth.realmId, cmd.id)
			.toRepresentation()
			.asModel()
			.asResult()
	}

	private fun UserRepresentation.asModel(): UserModel {
		return UserModel(
			id = this.id,
			email = this.email,
		)
	}

	private fun UserModel.asResult(): UserGetOneQueryResult {
		return UserGetOneQueryResult(this)
	}

}