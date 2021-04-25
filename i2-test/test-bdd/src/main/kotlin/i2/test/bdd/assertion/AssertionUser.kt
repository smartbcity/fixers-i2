package i2.test.bdd.assertion

import org.assertj.core.api.Assertions
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.UserRepresentation


fun AssertionKC.Companion.user(keycloak: Keycloak): AssertionUser = AssertionUser(keycloak)

class AssertionUser(
	private val keycloak: Keycloak,
) {
	companion object

	fun exist(realmId: String, id: String) {
		try {
			val user = getUserRepresentation(realmId, id)
			Assertions.assertThat(user).isNotNull
		} catch (e: javax.ws.rs.NotFoundException) {
			Assertions.fail("User[${id} not found]", e)
		}
	}


	fun isDisabled(realmId: String, id: String) {
		try {
			val user = getUserRepresentation(realmId, id)
			Assertions.assertThat(user.isEnabled).isFalse()
		} catch (e: javax.ws.rs.NotFoundException) {
			Assertions.fail("Realm[${id} not found]", e)
		}
	}


	fun notExist(realmId: String, id: String) {
		try {
			getUserRepresentation(realmId, id)
			Assertions.fail("Realm[${id} exist]")
		} catch (e: javax.ws.rs.NotFoundException) {
			Assertions.assertThat(true).isTrue
		}
	}

	private fun getUserRepresentation(
		realmId: String,
		id: String,
	): UserRepresentation  {
		val l = keycloak.realm(realmId).users().list()
		return keycloak.realm(realmId).users().get(id).toRepresentation()
	}

}