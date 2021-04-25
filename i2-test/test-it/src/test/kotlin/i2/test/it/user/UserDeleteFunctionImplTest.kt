package i2.test.it.user

import f2.function.spring.invokeSingle
import i2.keycloak.realm.domain.features.command.UserCreateCommand
import i2.keycloak.realm.domain.features.command.UserDeleteCommand
import i2.keycloak.realm.domain.features.command.UserDisableCommand
import i2.s2.user.create.UserCreateFunctionImpl
import i2.s2.user.f2.UserDeleteFunctionImpl
import i2.s2.user.f2.UserDisableFunctionImpl
import i2.test.bdd.assertion.AssertionKC
import i2.test.bdd.assertion.user
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.realm
import i2.test.bdd.given.user
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*

internal class UserDeleteFunctionImplTest: I2KeycloakTest() {

	val client = GivenKC().auth().withMasterRealmClient()
	val realmId = GivenKC(client).realm().withTestRealm()
	val userId = GivenKC(client).user().withUser(realmId, UUID.randomUUID().toString())

	@Test
	fun `should disable user`(): Unit = runBlocking {

		val cmd = UserDeleteCommand(
			id = userId,
			realmId = realmId,
			auth = client.auth
		)
		UserDeleteFunctionImpl().userDeleteFunction().invokeSingle(cmd)


		AssertionKC.user(client.keycloak).notExist(realmId, userId)
	}
}
