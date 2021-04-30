package i2.test.it.role

import f2.function.spring.invokeSingle
import i2.s2.role.f2.RoleCreateFunctionImpl
import i2.test.bdd.assertion.AssertionKC
import i2.test.bdd.assertion.role
import i2.test.bdd.data.DataTest
import i2.test.bdd.data.role.roleCreateCommand
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class RoleCreateFunctionImplTest: I2KeycloakTest() {

	private val masterClient = GivenKC().auth().withMasterRealmClient()

	@Test
	fun `should create a realm role`() = runBlocking {
		val cmd = DataTest.roleCreateCommand(
			auth = masterClient.auth,
			isClientRole = false,
//			composites = listOf(UUID.randomUUID().toString(), UUID.randomUUID().toString())
		)
		RoleCreateFunctionImpl().roleCreateFunction().invokeSingle(cmd)

		AssertionKC.role(masterClient.keycloak).assertThat(masterClient.auth.realmId, cmd.id).hasFields(
			description = cmd.description,
			isClientRole = cmd.isClientRole,
			composites = cmd.composites
		)
	}
}