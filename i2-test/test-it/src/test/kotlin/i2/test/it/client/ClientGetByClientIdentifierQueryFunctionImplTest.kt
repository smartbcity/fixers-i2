package i2.test.it.client

import f2.dsl.fnc.invoke
import i2.keycloak.f2.client.app.ClientGetByClientIdentifierQueryFunctionImpl
import i2.keycloak.f2.client.app.ClientGetByIdQueryFunctionImpl
import i2.keycloak.f2.client.domain.features.query.ClientGetByClientIdentifierQuery
import i2.keycloak.f2.client.domain.features.query.ClientGetByIdQuery
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.client
import i2.test.bdd.given.realm
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class ClientGetByClientIdentifierQueryFunctionImplTest: I2KeycloakTest() {

	private val masterClient = GivenKC().auth().withMasterRealmClient()
	private val realmId = GivenKC(masterClient).realm().withTestRealm()


	@Test
	fun `should get client when exists`(): Unit = runBlocking {
		val clientIdentifier = UUID.randomUUID().toString()
		GivenKC(masterClient).client().withClient(realmId, clientIdentifier = clientIdentifier)


		val cmd = ClientGetByClientIdentifierQuery(
			clientIdentifier = clientIdentifier,
			realmId = realmId,
			auth = masterClient.auth
		)
		val result = ClientGetByClientIdentifierQueryFunctionImpl().clientGetByClientIdentifierQueryFunction().invoke(cmd)

		Assertions.assertThat(result.client).isNotNull
	}

	@Test
	fun `should not get client when not exists`(): Unit = runBlocking {
		val cmd = ClientGetByIdQuery(
			id = "NOT_EXISTING_CLIENT",
			realmId = realmId,
			auth = masterClient.auth
		)
		val result = ClientGetByIdQueryFunctionImpl().clientGetByIdQueryFunction().invoke(cmd)

		Assertions.assertThat(result.client).isNull()
	}
}
