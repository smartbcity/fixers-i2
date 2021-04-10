package i2.test.bdd.given

import f2.function.spring.invokeSingle
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.domain.UserId
import i2.keycloak.realm.domain.features.command.UserCreateCommand
import i2.s2.user.create.UserCreateFunctionImpl

class GivenUser(
	private val client: AuthRealmClient
) {
	suspend fun GivenKC.withUser(realmId: RealmId, username: String): UserId {
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
		return UserCreateFunctionImpl().userCreateFunction().invokeSingle(cmd).id
	}
}

fun GivenKC.user() = GivenUser(client)