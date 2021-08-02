package i2.test.it.user

import f2.dsl.cqrs.base.PageRequestBase
import f2.dsl.fnc.invoke
import i2.keycloak.realm.domain.features.query.UserGetPageQuery
import i2.s2.user.f2.UserGetPageQueryFunctionImpl
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.realm
import i2.test.bdd.given.user
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class UserGetPageQueryFunctionImplTest: I2KeycloakTest() {

	val client = GivenKC().auth().withMasterRealmClient()
	val realmId = GivenKC(client).realm().withRealmId(UUID.randomUUID().toString())

	@Test
	fun `should get page of user`(): Unit = runBlocking {

		(0..6).forEach { _ ->
			GivenKC(client).user().withUser(realmId, UUID.randomUUID().toString())
		}


		val cmd = UserGetPageQuery(
			realmId = realmId,
			auth = client.auth,
			page = PageRequestBase(
				page = 0,
				size = 5
			)
		)
		val result = UserGetPageQueryFunctionImpl().userGetPageQueryFunctionImpl().invoke(cmd)

		Assertions.assertThat(result.page.list).hasSize(5)
		Assertions.assertThat(result.page.total).isEqualTo(7)
	}

}
