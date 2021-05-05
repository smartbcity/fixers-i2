package i2.test.it.role

import f2.dsl.cqrs.base.PageRequestBase
import f2.function.spring.invokeSingle
import i2.s2.role.domain.features.query.RoleGetPageQuery
import i2.s2.role.f2.RoleGetPageQueryFunctionImpl
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.realm
import i2.test.bdd.given.role
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class RoleGetPageQueryFunctionImplTest: I2KeycloakTest() {

	private val client = GivenKC().auth().withMasterRealmClient()
	private val realmId = GivenKC(client).realm().withTestRealm()

	@Test
	fun `should get page of role`(): Unit = runBlocking {
		val existingRoles = client.roles().list()
		val newRoles = (0..6).map {
			GivenKC(client).role().withRole(realmId, UUID.randomUUID().toString())
		}

		val cmd = RoleGetPageQuery(
			realmId = realmId,
			auth = client.auth,
			page = PageRequestBase(
				page = 0,
				size = 5
			)
		)
		val result = RoleGetPageQueryFunctionImpl().roleGetPageQueryFunctionImpl().invokeSingle(cmd)

		Assertions.assertThat(result.page.list).hasSize(cmd.page.size!!)
		Assertions.assertThat(result.page.total).isEqualTo(existingRoles.size + newRoles.size.toLong())
	}

}
