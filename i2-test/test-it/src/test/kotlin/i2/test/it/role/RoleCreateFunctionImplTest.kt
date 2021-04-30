package i2.test.it.role

import f2.function.spring.invokeSingle
import i2.s2.role.f2.RoleCreateFunctionImpl
import i2.test.bdd.assertion.AssertionKC
import i2.test.bdd.assertion.role
import i2.test.bdd.data.DataTest
import i2.test.bdd.data.role.roleCreateCommand
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.role
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class RoleCreateFunctionImplTest: I2KeycloakTest() {

	private val masterClient = GivenKC().auth().withMasterRealmClient()

	@Test
	fun `should create a realm role`(): Unit = runBlocking {
		val cmd = DataTest.roleCreateCommand(
			auth = masterClient.auth,
			isClientRole = false,
		)
		RoleCreateFunctionImpl().roleCreateFunction().invokeSingle(cmd)

		AssertionKC.role(masterClient.keycloak).assertThat(masterClient.auth.realmId, cmd.id).hasFields(
			description = cmd.description,
			isClientRole = cmd.isClientRole,
			compositeNames = cmd.composites
		)
	}

	@Test
	fun `should create a realm role with composites`(): Unit = runBlocking {
		val givenRole = GivenKC(masterClient).role()
		val compositeRoles = listOf(
			givenRole.withRole(UUID.randomUUID().toString()),
			givenRole.withRole(UUID.randomUUID().toString()),
			givenRole.withRole(UUID.randomUUID().toString())
		)

		val cmd = DataTest.roleCreateCommand(
			auth = masterClient.auth,
			isClientRole = false,
			composites = compositeRoles
		)
		RoleCreateFunctionImpl().roleCreateFunction().invokeSingle(cmd)

		AssertionKC.role(masterClient.keycloak).assertThat(masterClient.auth.realmId, cmd.id).hasFields(
			description = cmd.description,
			isClientRole = cmd.isClientRole,
			compositeNames = cmd.composites
		)
	}

	@Test
	fun `should not create a realm role when its composites does not exist`(): Unit = runBlocking {
		val cmd = DataTest.roleCreateCommand(
			auth = masterClient.auth,
			isClientRole = false,
			composites = listOf(UUID.randomUUID().toString(), UUID.randomUUID().toString())
		)

		Assertions.assertThatThrownBy { runBlocking {
			RoleCreateFunctionImpl().roleCreateFunction().invokeSingle(cmd)
		}}.isNotNull
	}
}