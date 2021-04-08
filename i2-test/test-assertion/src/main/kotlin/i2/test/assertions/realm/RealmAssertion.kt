package i2.test.assertions.realm

import org.assertj.core.api.Assertions
import org.keycloak.admin.client.Keycloak


fun AssertionKC.Companion.realm(keycloak: Keycloak): RealmAssertion = RealmAssertion(keycloak)

class RealmAssertion(
	private val keycloak: Keycloak) {
	companion object

	fun exist(id: String) {
		try {
			val realm = keycloak.realm(id).toRepresentation()
			Assertions.assertThat(realm).isNotNull
		} catch (e: javax.ws.rs.NotFoundException) {
			Assertions.fail("Realm[${id} not found]", e)
		}
	}

	fun notExist(id: String) {
		try {
			val realm = keycloak.realm(id).toRepresentation()
			Assertions.fail("Realm[${id} exist]")
		} catch (e: javax.ws.rs.NotFoundException) {
			Assertions.assertThat(true).isTrue
		}
	}

}