package i2.test.bdd.given

import i2.keycloak.master.domain.AuthRealmClientSecret
import i2.keycloak.master.domain.AuthRealmPassword
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.AuthRealmClientBuilder

class GivenAuth(
) {
	companion object {
		val SERVER_URL_TEST = "http://localhost:8080/auth"
	}

	fun withMasterRealmClient(): AuthRealmClient {
		val auth = AuthRealmPassword(
			serverUrl = SERVER_URL_TEST,
			clientId = "admin-cli",
			username = "admin",
			password = "admin",
			realm = "master",
			redirectUrl = "http://localhost:3000",
		)
		return AuthRealmClientBuilder().build(auth)
	}

	fun withRealmClient(realm: RealmId): AuthRealmClient {
		val auth = AuthRealmClientSecret(
			serverUrl = SERVER_URL_TEST,
			clientId = "admin-cli",
			clientSecret = "test",
			realm = realm,
			redirectUrl = ""
		)
		return AuthRealmClientBuilder().build(auth)
	}

}

fun GivenKC.auth(): GivenAuth = GivenAuth()