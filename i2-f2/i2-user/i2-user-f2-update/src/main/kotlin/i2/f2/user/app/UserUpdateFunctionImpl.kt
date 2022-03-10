package i2.f2.user.app

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import f2.dsl.fnc.invokeWith
import i2.commons.utils.toJson
import i2.f2.config.I2KeycloakConfig
import i2.f2.user.domain.features.command.UserUpdateCommand
import i2.f2.user.domain.features.command.UserUpdateFunction
import i2.f2.user.domain.features.command.UserUpdatedResult
import i2.keycloak.f2.user.domain.features.command.UserEmailSendActionsCommand
import i2.keycloak.f2.user.domain.features.command.UserEmailSendActionsFunction
import i2.keycloak.master.domain.AuthRealm
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private typealias KeycloakUserUpdateCommand = i2.keycloak.f2.user.domain.features.command.UserUpdateCommand
private typealias KeycloakUserUpdateFunction = i2.keycloak.f2.user.domain.features.command.UserUpdateFunction

@Configuration
class UserUpdateFunctionImpl(
	private val authRealm: AuthRealm,
	private val keycloakUserUpdateFunction: KeycloakUserUpdateFunction,
	private val i2KeycloakConfig: I2KeycloakConfig,
	private val userEmailSendActionsFunction: UserEmailSendActionsFunction
) {

	@Bean
	fun i2UserUpdateFunction(): UserUpdateFunction = f2Function { cmd ->
		keycloakUserUpdateFunction.invoke(cmd.toKeycloakUserUpdateCommand())

		if (cmd.sendEmailLink) {
			UserEmailSendActionsCommand(
				userId = cmd.id,
				clientId = null,
				redirectUri = null,
				actions = listOf("UPDATE_PASSWORD"),
				realmId = i2KeycloakConfig.realm,
				auth = authRealm
			).invokeWith(userEmailSendActionsFunction)
		}
		UserUpdatedResult(cmd.id)
	}

	private fun UserUpdateCommand.toKeycloakUserUpdateCommand() = KeycloakUserUpdateCommand(
		userId = id,
		email = email,
		firstname = givenName,
		lastname = familyName,
		metadata = listOfNotNull(
			::address.name to address.toJson(),
			phone?.let { ::phone.name to it }
		).toMap(),
		realmId = i2KeycloakConfig.realm,
		auth = authRealm
	)
}
