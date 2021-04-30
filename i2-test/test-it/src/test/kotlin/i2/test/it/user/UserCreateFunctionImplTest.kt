package i2.test.it.user

import f2.function.spring.invokeSingle
import i2.s2.user.create.UserCreateFunctionImpl
import i2.test.bdd.assertion.AssertionKC
import i2.test.bdd.assertion.user
import i2.test.bdd.data.DataTest
import i2.test.bdd.data.user.userCreateCommand
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.realm
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class UserCreateFunctionImplTest : I2KeycloakTest() {
	val clientMaster = GivenKC().auth().withMasterRealmClient()
	val realmId = GivenKC(clientMaster).realm().withTestRealm()

	@Test
	fun `should not find not existing user`(): Unit = runBlocking {
		AssertionKC.user(clientMaster.keycloak).notExists(realmId, UUID.randomUUID().toString())
	}

	@Test
	fun `should create user`(): Unit = runBlocking {
		val userUuid = "user-${UUID.randomUUID()}"
		val cmd = DataTest.userCreateCommand(
			realmId = realmId,
			auth = clientMaster.auth,
			username = "username-${userUuid}",
			firstname = "Jerry",
			lastname = "Pait",
			email = "jerry@pait.com",
			isEnable = true,
			metadata = mapOf(
				"organizationId" to UUID.randomUUID().toString()
			),
		)
		val event = UserCreateFunctionImpl().userCreateFunction().invokeSingle(cmd)

		Assertions.assertThat(event.id).isNotNull
		AssertionKC.user(clientMaster.keycloak).exists(realmId, event.id)
		AssertionKC.user(clientMaster.keycloak).assertThat(realmId, event.id).hasFields(
			userId = event.id,
			username = cmd.username,
			firstname = cmd.firstname,
			lastname = cmd.lastname,
			email = cmd.email,
			isEnable = cmd.isEnable,
			metadata = cmd.metadata
		)
	}
}
