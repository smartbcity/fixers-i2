package i2.f2.user.app

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import f2.dsl.fnc.invokeWith
import i2.commons.utils.toJson
import i2.f2.config.I2KeycloakConfig
import i2.f2.user.domain.features.command.UserCreateCommand
import i2.f2.user.domain.features.command.UserCreateFunction
import i2.f2.user.domain.features.command.UserCreatedResult
import i2.keycloak.f2.user.domain.features.command.UserEmailSendActionsCommand
import i2.keycloak.f2.user.domain.features.command.UserEmailSendActionsFunction
import i2.keycloak.f2.user.domain.features.command.UserJoinGroupCommand
import i2.keycloak.f2.user.domain.features.command.UserJoinGroupFunction
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantCommand
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantFunction
import i2.keycloak.master.domain.AuthRealm
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private typealias KeycloakUserCreateCommand = i2.keycloak.f2.user.domain.features.command.UserCreateCommand
private typealias KeycloakUserCreateFunction = i2.keycloak.f2.user.domain.features.command.UserCreateFunction

@Configuration
class UserCreateFunctionImpl(
	private val authRealm: AuthRealm,
	private val keycloakUserCreateFunction: KeycloakUserCreateFunction,
	private val i2KeycloakConfig: I2KeycloakConfig,
	private val userEmailSendActionsFunction: UserEmailSendActionsFunction,
	private val userJoinGroupFunction: UserJoinGroupFunction,
	private val userRolesGrantFunction: UserRolesGrantFunction
) {

	@Bean
	fun i2UserCreateFunction(): UserCreateFunction = f2Function { cmd ->
		val userId = keycloakUserCreateFunction.invoke(cmd.toKeycloakUserCreateCommand()).id

		cmd.memberOf?.let {
			UserJoinGroupCommand(
				id = userId,
				groupId = it,
				realmId = i2KeycloakConfig.realm,
				auth = authRealm
			).invokeWith(userJoinGroupFunction)
		}

		UserRolesGrantCommand(
			id = userId,
			roles = cmd.roles,
			auth = authRealm,
			realmId = i2KeycloakConfig.realm
		).invokeWith(userRolesGrantFunction)

		if (cmd.sendEmailLink) {
			UserEmailSendActionsCommand(
				userId = userId,
				clientId = null,
				redirectUri = null,
				actions = listOf("UPDATE_PASSWORD"),
				realmId = i2KeycloakConfig.realm,
				auth = authRealm
			).invokeWith(userEmailSendActionsFunction)
		}
		UserCreatedResult(userId)
	}

	private fun UserCreateCommand.toKeycloakUserCreateCommand() = KeycloakUserCreateCommand(
		username = email,
		firstname = givenName,
		lastname = familyName,
		email = email,
		isEnable = true,
		metadata = listOfNotNull(
			address?.let { ::address.name to address.toJson() },
			phone?.let { ::phone.name to it },
			::sendEmailLink.name to sendEmailLink.toJson(),
			memberOf?.let { ::memberOf.name to it }
		).toMap(),
		realmId = i2KeycloakConfig.realm,
		auth = authRealm
	)
}
