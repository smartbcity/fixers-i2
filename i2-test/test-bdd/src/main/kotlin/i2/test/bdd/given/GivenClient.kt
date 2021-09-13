package i2.test.bdd.given

import f2.dsl.fnc.invoke
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.s2.client.domain.ClientIdentifier
import i2.s2.client.f2.ClientCreateFunctionImpl
import i2.test.bdd.data.DataTest
import i2.test.bdd.data.client.clientCreateCommand
import kotlinx.coroutines.runBlocking
import java.util.*

class GivenClient(
    private val client: AuthRealmClient,
) {

	fun withClient(
        realmId: RealmId,
        clientIdentifier: ClientIdentifier = UUID.randomUUID().toString(),
    ) = runBlocking {
		val cmd = DataTest.clientCreateCommand(
			realmId = realmId,
			clientIdentifier = clientIdentifier,
			auth = client.auth
		)
		ClientCreateFunctionImpl().clientCreateFunction().invoke(cmd).id
	}
}

fun GivenKC.client() = GivenClient(client)
