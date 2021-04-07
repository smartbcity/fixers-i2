package i2.s2.user.resetpassword

import f2.function.spring.adapter.f2Function
import i2.keycloak.realm.client.config.ServiceRealmClientBuilder
import i2.keycloak.realm.domain.features.command.*
import org.keycloak.representations.idm.CredentialRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserResetPasswordFunctionImpl {

	@Bean
	fun userResetPasswordFunction(): UserResetPasswordFunction = f2Function { cmd ->
		val realmClient = ServiceRealmClientBuilder().build(cmd.auth)

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