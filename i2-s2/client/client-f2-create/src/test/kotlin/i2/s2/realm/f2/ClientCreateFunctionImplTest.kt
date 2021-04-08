package i2.s2.realm.f2

import f2.function.spring.invokeSingle
import i2.keycloak.master.domain.AuthRealmPassword
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.s2.client.domain.features.command.ClientCreateCommand
import i2.s2.client.f2.ClientCreateFunctionImpl
import i2.test.assertions.realm.AssertionKC
import i2.test.assertions.realm.GivenKC
import i2.test.assertions.realm.client
import i2.test.assertions.realm.realm
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class ClientCreateFunctionImplTest {

	val auth = AuthRealmPassword (
		serverUrl = "http://localhost:8080/auth",
		realm = "master",
		clientId = "admin-cli",
		username = "admin",
		password = "admin",
		redirectUrl = "http://localhost:3000"
	)
	val masterRealm = AuthRealmClientBuilder().build(auth)


	@Test
	fun `should create a new realm`(): Unit = runBlocking {
		val client = GivenKC.client().withMasterRealmClient()
		GivenKC.realm(client).withTestRealm()

		val id = "client-${UUID.randomUUID()}"
		val cmd = ClientCreateCommand(
			id = id,
			realmId = "test",
			auth = client.auth
		)

		val event = ClientCreateFunctionImpl().clientCreateFunction().invokeSingle(cmd)
		Assertions.assertThat(event.id).isEqualTo(id)
		AssertionKC.client(masterRealm.keycloak).exist("test", id)
	}
}