package i2.test.it.client

import f2.function.spring.invokeSingle
import i2.s2.client.f2.ClientUpdateFunctionImpl
import i2.s2.errors.I2Exception
import i2.test.bdd.assertion.AssertionKC
import i2.test.bdd.assertion.client
import i2.test.bdd.data.DataTest
import i2.test.bdd.data.client.clientUpdateUrisCommand
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.client
import i2.test.bdd.given.realm
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class ClientUpdateUrisFunctionImplTest: I2KeycloakTest() {

    private val masterClient = GivenKC().auth().withMasterRealmClient()
    private val realmId = GivenKC(masterClient).realm().withRealmId(UUID.randomUUID().toString())

    private val clientId = GivenKC(masterClient).client().withClient(realmId)

    @Test
    fun `should update uris when client exists`(): Unit = runBlocking {
        val command = DataTest.clientUpdateUrisCommand(
            realmId = realmId,
            auth = masterClient.auth,
            id = clientId,
            rootUrl = "https://rootUrl-$clientId.com",
            redirectUris = listOf("https://redirectUris-$clientId.com"),
            baseUrl = "https://baseUrl-$clientId.com",
        )
        ClientUpdateFunctionImpl().clientUpdateUrisFunction().invokeSingle(command)

        AssertionKC.client(masterClient.keycloak).assertThat(realmId, clientId).hasFields(
            rootUrl = command.rootUrl,
            redirectUris = command.redirectUris,
            baseUrl = command.baseUrl
        )
    }

    @Test
    fun `should do nothing when client does not exist`() {
        val command = DataTest.clientUpdateUrisCommand(
            realmId = realmId,
            auth = masterClient.auth
        )

        Assertions.assertThatThrownBy { runBlocking {
            ClientUpdateFunctionImpl().clientUpdateUrisFunction().invokeSingle(command)
        }}.isInstanceOf(I2Exception::class.java)
    }
}