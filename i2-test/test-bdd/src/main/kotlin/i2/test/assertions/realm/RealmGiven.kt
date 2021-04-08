package i2.test.assertions.realm

import i2.keycloak.master.domain.AuthRealmClientSecret
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.keycloak.realm.client.config.buildRealmRepresentation
import i2.keycloak.realm.client.config.realmsResource
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.RealmRepresentation

class RealmGiven(
	private val client: AuthRealmClient,
) {
	companion object

	fun withTestRealm(): AuthRealmClient {
		withID("test")
		val auth = AuthRealmClientSecret(
			serverUrl = "http://localhost:8080/auth",
			clientId = "admin-cli",
			clientSecret = "test",
			realm = "realm",
			redirectUrl = ""
		)
		return AuthRealmClientBuilder().build(auth)
	}

	fun withID(id: String) {
		try {
			val realm = getRealmRepresentation(id)
			Assertions.assertThat(realm).isNotNull
		} catch (e: javax.ws.rs.NotFoundException) {
			create(id)
		}
	}

	private fun create(id: String): RealmRepresentation? {
		return try {
			val realm = client.buildRealmRepresentation(
				realm = id
			)
			client.realmsResource().create(realm)
			getRealmRepresentation(id)
		} catch (e: javax.ws.rs.NotFoundException) {
			Assertions.fail<Unit>("Error initializing realm [${id}]")
			null
		}
	}

	private fun getRealmRepresentation(id: String) = client.keycloak.realm(id).toRepresentation()

}

fun GivenKC.Companion.realm(
	client: AuthRealmClient = GivenKC.client().withMasterRealmClient()
): RealmGiven = RealmGiven(client)