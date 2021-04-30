package i2.test.bdd.given

import f2.function.spring.invokeSingle
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.domain.UserId
import i2.keycloak.realm.domain.features.command.UserCreateCommand
import i2.s2.user.create.UserCreateFunctionImpl
import i2.test.bdd.data.DataTest
import i2.test.bdd.data.user.userCreateCommand
import kotlinx.coroutines.runBlocking

class GivenUser(
	private val client: AuthRealmClient
) {
	fun withUser(realmId: RealmId, username: String): UserId = withUser(
		DataTest.userCreateCommand(
			realmId = realmId,
			auth = client.auth,
			username = username
		)
	)

	fun withUser(cmd: UserCreateCommand): UserId = runBlocking {
		UserCreateFunctionImpl().userCreateFunction().invokeSingle(cmd).id
	}
}

fun GivenKC.user() = GivenUser(client)