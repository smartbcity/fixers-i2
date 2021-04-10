package i2.test.bdd.assertion

import org.assertj.core.api.Assertions
import org.keycloak.admin.client.Keycloak


fun AssertionKC.Companion.auth(keycloak: Keycloak): AssertionClient = AssertionClient(keycloak)

class AssertionClient(
	private val keycloak: Keycloak) {
	companion object

	fun exist(realmId: String, clientId: String) {
		try {
			val realm = keycloak.realm(realmId).clients().findByClientId(clientId).first()
			Assertions.assertThat(realm).isNotNull
		} catch (e: javax.ws.rs.NotFoundException) {
			Assertions.fail("Client[${clientId} not found]", e)
		}
	}

	fun notExist(realmId: String, id: String) {
		try {
			val realm = keycloak.realm(realmId).clients().get(id).toRepresentation()
			Assertions.fail("Realm[${id} exist]")
		} catch (e: javax.ws.rs.NotFoundException) {
			Assertions.assertThat(true).isTrue
		}
	}


}