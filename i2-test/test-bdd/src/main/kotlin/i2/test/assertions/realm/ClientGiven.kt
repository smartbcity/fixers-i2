package i2.test.assertions.realm

import i2.keycloak.master.domain.AuthRealmPassword
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.AuthRealmClientBuilder

class AuthGiven(
) {
	companion object

	fun withMasterRealmClient(): AuthRealmClient {
		val auth = AuthRealmPassword(
			serverUrl = "http://localhost:8080/auth",
			clientId = "admin-cli",
			username = "admin",
			password = "admin",
			realm = "master",
			redirectUrl = "http://localhost:3000",
		)
		return AuthRealmClientBuilder().build(auth)
	}


}

fun GivenKC.Companion.client(): AuthGiven = AuthGiven()