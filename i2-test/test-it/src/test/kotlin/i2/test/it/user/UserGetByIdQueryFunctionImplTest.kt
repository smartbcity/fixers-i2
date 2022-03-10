package i2.test.it.user

import f2.dsl.fnc.invoke
import i2.keycloak.f2.user.domain.features.query.UserGetByIdQuery
import i2.keycloak.f2.user.domain.features.query.UserGetByIdQueryFunction
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.realm
import i2.test.bdd.given.user
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID

class UserGetByIdQueryFunctionImplTest: I2KeycloakTest() {

	private val client = GivenKC().auth().withMasterRealmClient()
	private val realmId = GivenKC(client).realm().withTestRealm()

	@Autowired
	private lateinit var userGetByIdQueryFunction: UserGetByIdQueryFunction

	private val userId = GivenKC(client).user().withUser(realmId, UUID.randomUUID().toString())

	@Test
	fun `should get user when exists`(): Unit = runBlocking {
		val cmd = UserGetByIdQuery(
			id = userId,
			realmId = realmId,
			auth = client.auth
		)
		val result = userGetByIdQueryFunction.invoke(cmd)

		Assertions.assertThat(result.user).isNotNull()
	}

	@Test
	fun `should not get user when not exists`(): Unit = runBlocking {
		val cmd = UserGetByIdQuery(
			id = "NOT_EXISTING_USER",
			realmId = realmId,
			auth = client.auth
		)
		val result = userGetByIdQueryFunction.invoke(cmd)

		Assertions.assertThat(result.user).isNull()
	}
}
