package i2.f2.user.app

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.f2.config.I2KeycloakConfig
import i2.f2.user.domain.features.command.UserResetPasswordCommand
import i2.f2.user.domain.features.command.UserResetPasswordFunction
import i2.f2.user.domain.features.command.UserResetPasswordResult
import i2.keycloak.master.domain.AuthRealm
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private typealias KeycloakUserResetPasswordCommand = i2.keycloak.f2.user.domain.features.command.UserResetPasswordCommand
private typealias KeycloakUserResetPasswordFunction = i2.keycloak.f2.user.domain.features.command.UserResetPasswordFunction

@Configuration
class UserResetPasswordFunctionImpl(
	private val authRealm: AuthRealm,
	private val keycloakUserResetPasswordFunction: KeycloakUserResetPasswordFunction,
	private val i2KeycloakConfig: I2KeycloakConfig
) {

	@Bean
	fun i2UserResetPasswordFunction(): UserResetPasswordFunction = f2Function { cmd ->
		keycloakUserResetPasswordFunction.invoke(cmd.toKeycloakUserResetPasswordCommand())
		UserResetPasswordResult(cmd.id)
	}

	private fun UserResetPasswordCommand.toKeycloakUserResetPasswordCommand() = KeycloakUserResetPasswordCommand(
		userId = id,
		password = password,
		realmId = i2KeycloakConfig.realm,
		auth = authRealm
	)
}
