package i2.test.it.user

import f2.function.spring.invokeSingle
import i2.keycloak.realm.domain.features.command.UserDeleteCommand
import i2.s2.user.f2.UserDeleteFunctionImpl
import i2.test.bdd.assertion.AssertionKC
import i2.test.bdd.assertion.user
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.realm
import i2.test.bdd.given.user
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.UUID

internal class UserDeleteFunctionImplTest: I2KeycloakTest() {

	val client = GivenKC().auth().withMasterRealmClient()
	val realmId = GivenKC(client).realm().withTestRealm()

	val userId = GivenKC(client).user().withUser(realmId, UUID.randomUUID().toString())

	@Test
	fun `should delete user`(): Unit = runBlocking {

		val cmd = UserDeleteCommand(
			id = userId,
			realmId = realmId,
			auth = client.auth
		)
		UserDeleteFunctionImpl().userDeleteFunction().invokeSingle(cmd)


		AssertionKC.user(client.keycloak).notExists(realmId, userId)
	}
}
