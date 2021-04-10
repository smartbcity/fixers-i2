package i2.test.bdd.given

import i2.keycloak.master.domain.AuthRealmPassword
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.AuthRealmClientBuilder

class GivenClient {

}

fun GivenKC.client() = GivenClient()