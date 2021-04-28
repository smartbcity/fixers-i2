package i2.test.bdd.given

import f2.function.spring.invokeSingle
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.s2.client.f2.ClientCreateFunctionImpl
import i2.test.bdd.data.DataTest
import i2.test.bdd.data.client.clientCreateCommand
import kotlinx.coroutines.runBlocking

class GivenClient(
    private val client: AuthRealmClient
) {

    fun withClient(realmId: RealmId) = runBlocking {
        val cmd = DataTest.clientCreateCommand(
            realmId = realmId,
            auth = client.auth
        )
        ClientCreateFunctionImpl().clientCreateFunction().invokeSingle(cmd).id
    }
}

fun GivenKC.client() = GivenClient(client)