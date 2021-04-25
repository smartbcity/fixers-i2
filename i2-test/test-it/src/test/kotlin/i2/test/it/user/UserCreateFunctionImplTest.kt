package i2.test.it.user

import f2.function.spring.invokeSingle
import i2.keycloak.realm.domain.features.command.UserCreateCommand
import i2.s2.user.create.UserCreateFunctionImpl
import i2.test.bdd.testcontainers.I2KeycloakTest
import i2.test.bdd.assertion.AssertionKC
import i2.test.bdd.assertion.user
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.realm
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class UserCreateFunctionImplTest : I2KeycloakTest() {


	val clientMaster = GivenKC().auth().withMasterRealmClient()
	val realmId = GivenKC(clientMaster).realm().withTestRealm()


	@Test
	fun `should not find not existing user `(): Unit = runBlocking {

		AssertionKC.user(clientMaster.keycloak).notExist(realmId, UUID.randomUUID().toString())
	}

	@Test
	fun `should create user`(): Unit = runBlocking {
		val userUuid = "user-${UUID.randomUUID()}"
		val cmd = UserCreateCommand(
			realmId = realmId,
			username = "username-${userUuid}",
			firstname = null,
			lastname = null,
			email = "${userUuid}@smartb.city",
			isEnable = true,
			metadata = mapOf(
				"organizationId" to UUID.randomUUID().toString()
			),
			auth = clientMaster.auth,
		)
		val event = UserCreateFunctionImpl().userCreateFunction().invokeSingle(cmd)

		Assertions.assertThat(event.id).isNotNull
		AssertionKC.user(clientMaster.keycloak).exist(realmId, event.id)
	}
}
