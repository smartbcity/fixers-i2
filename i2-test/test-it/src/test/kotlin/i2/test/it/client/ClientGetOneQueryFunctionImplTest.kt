package i2.test.it.client

import f2.function.spring.invokeSingle
import i2.s2.client.domain.features.query.ClientGetOneQuery
import i2.s2.client.f2.ClientGetOneQueryFunctionImpl
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.client
import i2.test.bdd.given.realm
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ClientGetOneQueryFunctionImplTest: I2KeycloakTest() {

	private val masterClient = GivenKC().auth().withMasterRealmClient()
	private val realmId = GivenKC(masterClient).realm().withTestRealm()

	private val clientId = GivenKC(masterClient).client().withClient(realmId)

	@Test
	fun `should get client when exists`(): Unit = runBlocking {
		val cmd = ClientGetOneQuery(
			id = clientId,
			realmId = realmId,
			auth = masterClient.auth
		)
		val result = ClientGetOneQueryFunctionImpl().clientGetOneQueryFunction().invokeSingle(cmd)

		Assertions.assertThat(result.client).isNotNull
	}

	@Test
	fun `should not get client when not exists`(): Unit = runBlocking {
		val cmd = ClientGetOneQuery(
			id = "NOT_EXISTING_CLIENT",
			realmId = realmId,
			auth = masterClient.auth
		)
		val result = ClientGetOneQueryFunctionImpl().clientGetOneQueryFunction().invokeSingle(cmd)

		Assertions.assertThat(result.client).isNull()
	}
}
