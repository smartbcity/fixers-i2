package i2.test.bdd.given

import i2.keycloak.master.domain.AuthRealmClientSecret
import i2.keycloak.master.domain.AuthRealmPassword
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.AuthRealmClientBuilder

class GivenAuth {
	companion object {
		const val SERVER_URL_TEST = "http://localhost:8080/auth"
	}

	fun withMasterRealmClient(realm: RealmId = "master"): AuthRealmClient {
		val auth = AuthRealmPassword(
			serverUrl = SERVER_URL_TEST,
			clientId = "admin-cli",
			username = "admin",
			password = "admin",
			realmId = realm,
			redirectUrl = "http://localhost:3000",
		)
		return AuthRealmClientBuilder().build(auth)
	}

	fun withRealmClient(realm: RealmId): AuthRealmClient {
		val auth = AuthRealmClientSecret(
			serverUrl = SERVER_URL_TEST,
			clientId = "admin-cli",
			clientSecret = "test",
			realmId = realm,
			redirectUrl = ""
		)
		return AuthRealmClientBuilder().build(auth)
	}
}

fun GivenKC.auth(): GivenAuth = GivenAuth()
