package i2.test.it.client

import f2.function.spring.invokeSingle
import i2.s2.client.domain.features.command.ClientGenerateSecretCommand
import i2.s2.client.f2.ClientGenerateSecretFunctionImpl
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.client
import i2.test.bdd.given.realm
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class ClientGenerateSecretFunctionImplTest: I2KeycloakTest() {

    private val masterClient = GivenKC().auth().withMasterRealmClient()
    private val realmId = GivenKC(masterClient).realm().withRealmId(UUID.randomUUID().toString())

    private val clientId = GivenKC(masterClient).client().withClient(realmId)

    @Test
    fun `should generate new secret`(): Unit = runBlocking {
        val cmd = ClientGenerateSecretCommand(
            id = clientId,
            realmId = realmId,
            auth = masterClient.auth
        )
        val secret = ClientGenerateSecretFunctionImpl().clientGenerateSecretFunction().invokeSingle(cmd).secret

        Assertions.assertThat(secret).isNotNull
    }
}