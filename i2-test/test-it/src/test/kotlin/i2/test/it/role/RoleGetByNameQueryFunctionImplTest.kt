package i2.test.it.role

import f2.dsl.fnc.invoke
import i2.s2.role.domain.features.query.RoleGetByNameQuery
import i2.s2.role.f2.RoleGetByNameQueryFunctionImpl
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.realm
import i2.test.bdd.given.role
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class RoleGetByNameQueryFunctionImplTest: I2KeycloakTest() {

	private val client = GivenKC().auth().withMasterRealmClient()
	private val realmId = GivenKC(client).realm().withTestRealm()

	private val roleName = GivenKC(client).role().withRole(realmId, UUID.randomUUID().toString())

	@Test
	fun `should get role when exists`(): Unit = runBlocking {
		val cmd = RoleGetByNameQuery(
			name = roleName,
			realmId = realmId,
			auth = client.auth
		)
		val result = RoleGetByNameQueryFunctionImpl().roleGetByNameQueryFunction().invoke(cmd)

		Assertions.assertThat(result.role).isNotNull
	}

	@Test
	fun `should not get role when not exists`(): Unit = runBlocking {
		val cmd = RoleGetByNameQuery(
			name = "NOT_EXISTING_ROLE",
			realmId = realmId,
			auth = client.auth
		)
		val result = RoleGetByNameQueryFunctionImpl().roleGetByNameQueryFunction().invoke(cmd)

		Assertions.assertThat(result.role).isNull()
	}
}
