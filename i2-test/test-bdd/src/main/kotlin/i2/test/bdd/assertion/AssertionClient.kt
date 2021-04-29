package i2.test.bdd.assertion

import i2.keycloak.master.domain.RealmId
import i2.s2.client.domain.ClientId
import org.assertj.core.api.Assertions
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.ClientRepresentation


fun AssertionKC.Companion.client(keycloak: Keycloak): AssertionClient = AssertionClient(keycloak)

class AssertionClient(
	private val keycloak: Keycloak
) {
	companion object

	fun exists(realmId: RealmId, id: ClientId) {
		try {
			val realm = keycloak.realm(realmId).clients().get(id)
			Assertions.assertThat(realm).isNotNull
		} catch (e: javax.ws.rs.NotFoundException) {
			Assertions.fail("Client[${id} not found]", e)
		}
	}

	fun notExists(realmId: RealmId, id: ClientId) {
		try {
			val realm = keycloak.realm(realmId).clients().get(id).toRepresentation()
			Assertions.fail("Client[${id} exists]")
		} catch (e: javax.ws.rs.NotFoundException) {
			Assertions.assertThat(true).isTrue
		}
	}

	fun assertThat(realmId: RealmId, id: ClientId): ClientComparator {
		exists(realmId, id)
		val client = getClientRepresentation(realmId, id)
		return ClientComparator(client)
	}

	private fun getClientRepresentation(realmId: RealmId, id: ClientId): ClientRepresentation {
		return keycloak.realm(realmId).clients().get(id).toRepresentation()
	}

	inner class ClientComparator(
		private val client: ClientRepresentation
	) {
		fun hasFields(
			userId: ClientId = client.id,
			rootUrl: String = client.rootUrl,
			redirectUris: List<String> = client.redirectUris,
			baseUrl: String? = client.baseUrl,
		) {
			Assertions.assertThat(client.id).isEqualTo(userId)
			Assertions.assertThat(client.rootUrl).isEqualTo(rootUrl)
			Assertions.assertThat(client.redirectUris).isEqualTo(redirectUris)
			Assertions.assertThat(client.baseUrl).isEqualTo(baseUrl)
		}
	}
}
