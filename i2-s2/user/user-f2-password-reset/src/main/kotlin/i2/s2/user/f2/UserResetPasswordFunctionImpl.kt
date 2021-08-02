package i2.s2.user.f2

import f2.dsl.fnc.f2Function
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.keycloak.realm.domain.features.command.*
import org.keycloak.representations.idm.CredentialRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserResetPasswordFunctionImpl {

	@Bean
	fun userResetPasswordFunction(): UserResetPasswordFunction = f2Function { cmd ->
		val realmClient = AuthRealmClientBuilder().build(cmd.auth)

		cmd.password?.let { password ->
			val credentials = CredentialRepresentation()
			credentials.type = CredentialRepresentation.PASSWORD
			credentials.isTemporary = true
			credentials.value = password
			realmClient.getUserResource(cmd.id).resetPassword(CredentialRepresentation())
		}
		UserResetPasswordResult(cmd.id)
	}

}