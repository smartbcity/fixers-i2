package i2.test.it.user

import f2.function.spring.invokeSingle
import i2.keycloak.realm.domain.features.query.UserGetOneQuery
import i2.s2.user.f2.UserGetOneQueryFunctionImpl
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.realm
import i2.test.bdd.given.user
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class UserGetOneQueryFunctionImplTest: I2KeycloakTest() {

	val client = GivenKC().auth().withMasterRealmClient()
	val realmId = GivenKC(client).realm().withTestRealm()

	val userId = GivenKC(client).user().withUser(realmId, UUID.randomUUID().toString())

	@Test
	fun `should get user when exists`(): Unit = runBlocking {

		val cmd = UserGetOneQuery(
			id = userId,
			realmId = realmId,
			auth = client.auth
		)
		val result = UserGetOneQueryFunctionImpl().userGetOneQueryFunction().invokeSingle(cmd)

		Assertions.assertThat(result.user).isNotNull()
	}

	@Test
	fun `should not get user when not exists`(): Unit = runBlocking {

		val cmd = UserGetOneQuery(
			id = "NOT_EXISTING_USER",
			realmId = realmId,
			auth = client.auth
		)
		val result = UserGetOneQueryFunctionImpl().userGetOneQueryFunction().invokeSingle(cmd)

		Assertions.assertThat(result.user).isNull()
	}
}
