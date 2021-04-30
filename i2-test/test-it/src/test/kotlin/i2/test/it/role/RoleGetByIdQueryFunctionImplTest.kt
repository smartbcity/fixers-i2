package i2.test.it.role

import f2.function.spring.invokeSingle
import i2.s2.role.domain.features.query.RoleGetByIdQuery
import i2.s2.role.f2.RoleGetByIdQueryFunctionImpl
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.role
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class RoleGetByIdQueryFunctionImplTest: I2KeycloakTest() {

	private val client = GivenKC().auth().withMasterRealmClient()

	private val roleId = GivenKC(client).role().withRole(UUID.randomUUID().toString())

	@Test
	fun `should get role when exists`(): Unit = runBlocking {
		val cmd = RoleGetByIdQuery(
			id = roleId,
			realmId = client.auth.realmId,
			auth = client.auth
		)
		val result = RoleGetByIdQueryFunctionImpl().roleGetByIdQueryFunction().invokeSingle(cmd)

		Assertions.assertThat(result.role).isNotNull
	}

	@Test
	fun `should not get role when not exists`(): Unit = runBlocking {
		val cmd = RoleGetByIdQuery(
			id = "NOT_EXISTING_ROLE",
			realmId = client.auth.realmId,
			auth = client.auth
		)
		val result = RoleGetByIdQueryFunctionImpl().roleGetByIdQueryFunction().invokeSingle(cmd)

		Assertions.assertThat(result.role).isNull()
	}
}
