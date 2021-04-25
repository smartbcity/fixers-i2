package i2.test.bdd.given

import f2.function.spring.invokeSingle
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.domain.UserId
import i2.keycloak.realm.domain.features.command.UserCreateCommand
import i2.s2.user.create.UserCreateFunctionImpl
import kotlinx.coroutines.runBlocking

class GivenUser(
	private val client: AuthRealmClient
) {
	fun withUser(realmId: RealmId, username: String): UserId = runBlocking {
		val cmd = UserCreateCommand(
			realmId = realmId,
			username = username,
			firstname = null,
			lastname = null,
			email = "",
			isEnable = true,
			metadata = emptyMap(),
			auth = client.auth
		)
		UserCreateFunctionImpl().userCreateFunction().invokeSingle(cmd).id
	}
}

fun GivenKC.user() = GivenUser(client)