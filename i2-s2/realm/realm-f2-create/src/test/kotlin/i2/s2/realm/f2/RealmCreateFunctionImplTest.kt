package i2.s2.realm.f2

import f2.function.spring.invokeSingle
import i2.s2.realm.domain.features.command.RealmCreateCommand
import i2.test.assertions.realm.AssertionKC
import i2.test.assertions.realm.GivenKC
import i2.test.assertions.realm.client
import i2.test.assertions.realm.realm
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.*

class RealmCreateFunctionImplTest {

	val masterClient = GivenKC.client().withMasterRealmClient()

	@Test
	fun `should failed realm`(): Unit = runBlocking {
		val id = "test-${UUID.randomUUID()}"
		AssertionKC.realm(masterClient.keycloak).notExist(id)
	}

	@Test
	fun `should create a new realm`(): Unit = runBlocking {
		val id = "test-${UUID.randomUUID()}"
		val cmd = RealmCreateCommand(
			id = id,
			theme = null,
			locale = null,
			smtpServer = null,
			masterRealmAuth = masterClient.auth
		)

		val event = RealmCreateFunctionImpl().realmCreateFunction().invokeSingle(cmd)
		AssertionKC.realm(masterClient.keycloak).exist(id)
	}
}