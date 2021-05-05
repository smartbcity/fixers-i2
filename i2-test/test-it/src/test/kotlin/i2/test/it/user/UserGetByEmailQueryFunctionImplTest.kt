package i2.test.it.user

import f2.function.spring.invokeSingle
import i2.keycloak.realm.domain.features.query.UserGetByEmailQuery
import i2.s2.user.f2.UserGetByEmailQueryFunctionImpl
import i2.test.bdd.data.DataTest
import i2.test.bdd.data.user.userCreateCommand
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.realm
import i2.test.bdd.given.user
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class UserGetByEmailQueryFunctionImplTest: I2KeycloakTest() {

	private val client = GivenKC().auth().withMasterRealmClient()
	private val realmId = GivenKC(client).realm().withTestRealm()

	@Test
	fun `should get user when user with email exists`(): Unit = runBlocking {
		val createCommand = DataTest.userCreateCommand(
			realmId = realmId,
			auth = client.auth,
			email = "${UUID.randomUUID()}@email.com"
		)
		GivenKC(client).user().withUser(createCommand)

		val cmd = UserGetByEmailQuery(
			email = createCommand.email,
			realmId = realmId,
			auth = client.auth
		)
		val result = UserGetByEmailQueryFunctionImpl().userGetByEmailQueryFunction().invokeSingle(cmd)

		Assertions.assertThat(result.user).isNotNull
	}

	@Test
	fun `should not get user when no user with email exists`(): Unit = runBlocking {
		val createCommand = DataTest.userCreateCommand(
			realmId = realmId,
			auth = client.auth,
			email = "${UUID.randomUUID()}@email.com"
		)
		GivenKC(client).user().withUser(createCommand)

		val cmd = UserGetByEmailQuery(
			email = "invalid-email@email.com",
			realmId = realmId,
			auth = client.auth
		)
		val result = UserGetByEmailQueryFunctionImpl().userGetByEmailQueryFunction().invokeSingle(cmd)

		Assertions.assertThat(result.user).isNull()
	}
}
