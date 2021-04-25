package i2.test.it.realm

import f2.function.spring.invokeSingle
import i2.s2.realm.domain.features.command.RealmGetOneQuery
import i2.s2.realm.f2.RealmGetOneQueryFunctionImpl
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.realm
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class RealmGetOneQueryFunctionImplTest : I2KeycloakTest() {

	val masterClient = GivenKC().auth().withMasterRealmClient()
	val realmId = GivenKC(masterClient).realm().withTestRealm()

	@Test
	fun `should return a not null realm when exists`(): Unit = runBlocking {
		val cmd = RealmGetOneQuery(
			id = realmId,
			authRealm = masterClient.auth
		)

		val found = RealmGetOneQueryFunctionImpl().realmGetOneQueryFunction().invokeSingle(cmd)
		Assertions.assertThat(found.realm).isNotNull

	}


	@Test
	fun `should return null realm when not exists`(): Unit = runBlocking {
		val cmd = RealmGetOneQuery(
			id = "NOT_EXISTING_REAML",
			authRealm = masterClient.auth
		)

		val found = RealmGetOneQueryFunctionImpl().realmGetOneQueryFunction().invokeSingle(cmd)
		Assertions.assertThat(found.realm).isNull()

	}
}