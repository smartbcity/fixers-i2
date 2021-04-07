package i2.s2.user.create

import f2.function.spring.adapter.f2Function
import i2.keycloak.realm.client.config.ServiceRealmClient
import i2.keycloak.realm.client.config.ServiceRealmClientBuilder
import i2.keycloak.realm.domain.features.command.UserCreateCommand
import i2.keycloak.realm.domain.features.command.UserCreateFunction
import i2.keycloak.realm.domain.features.command.UserCreatedResult
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.ws.rs.core.Response

@Configuration
class UserCreateFunctionImpl {

	@Bean
	fun userCreateFunction(): UserCreateFunction = f2Function { cmd ->
		val serviceClient = ServiceRealmClientBuilder().build(cmd.auth)
		createUser(serviceClient, cmd)

		UserCreatedResult(cmd.id)
	}

	fun createUser(client: ServiceRealmClient, user: UserCreateCommand): String {
		val userRepresentation = initUserRepresentation(user)
		val userId = createUser(client, userRepresentation, ::onUserCreationFailure)
		onboarding(client, user.id)
		return userId
	}

	fun createUser(client: ServiceRealmClient, user: UserRepresentation, onFailure: (response: Response) -> Unit = {}): String {
		val response = client.realm.users().create(user)
		if (response.isFailure()) {
			onFailure(response)
		}
		return response.toEntityCreatedId()
	}

	private fun initUserRepresentation(user: UserCreateCommand): UserRepresentation {
		val userRep = UserRepresentation()
		userRep.id = user.id
		userRep.username = user.username
		userRep.email = user.email
		userRep.firstName = user.firstname
		userRep.lastName = user.lastname
		userRep.isEnabled = true
		userRep.isEmailVerified = true
		user.metadata.forEach {
			userRep.singleAttribute(it.key, it.value)
		}
		return userRep
	}

	fun onboarding(client: ServiceRealmClient, userId: String) {
		executeEmailActions(client, userId, listOf("UPDATE_PASSWORD", "ONBOARDING"))
	}

	fun executeEmailActions(client: ServiceRealmClient, userId: String, actions: List<String>)  {
		val auth = client.auth
		client.getUserResource(userId).executeActionsEmail(auth.clientId, auth.redirectUrl, actions)
	}

	private fun Response.isFailure(): Boolean {
		return this.status < 200 || this.status > 299
	}

	private fun Response.toEntityCreatedId(): String {
		return this.location.toString().substringAfterLast("/")
	}

	private fun onUserCreationFailure(response: Response) = onCreationFailure(response, "user")

	private fun onCreationFailure(response: Response, entityName: String = "entity") {
		val message = "Error creating $entityName in identity provider (code: ${response.status}) }"
		throw UserCreationError(message)
	}
}