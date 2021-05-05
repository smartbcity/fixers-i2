package i2.test.it.import

import f2.function.spring.invokeSingle
import i2.s2.realm.domain.ClientImport
import i2.s2.realm.domain.RoleImport
import i2.s2.realm.domain.UserImport
import i2.s2.realm.domain.features.command.ImportDataCommand
import i2.s2.realm.domain.features.command.ImportDataFunction
import i2.test.bdd.assertion.AssertionKC
import i2.test.bdd.assertion.client
import i2.test.bdd.assertion.realm
import i2.test.bdd.assertion.role
import i2.test.bdd.assertion.user
import i2.test.bdd.data.DataTest
import i2.test.bdd.data.import.clientImport
import i2.test.bdd.data.import.realmImport
import i2.test.bdd.data.import.roleImport
import i2.test.bdd.data.import.userImport
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ImportDataFunctionImplTest: I2KeycloakTest() {

    @Autowired
    private lateinit var importDataFunction: ImportDataFunction

    private val masterClient = GivenKC().auth().withMasterRealmClient()

    @Test
    fun `should initialize all imported data`(): Unit = runBlocking {
        val subRole = DataTest.roleImport()
        val roles = listOf(
            DataTest.roleImport(composites = listOf(subRole.name)),
            DataTest.roleImport(),
            subRole
        )
        val roleIds = roles.map(RoleImport::name)

        val clients = listOf(
            DataTest.clientImport(isServiceAccountsEnabled = true, serviceAccountRoles = listOf(roleIds[0])),
            DataTest.clientImport(isServiceAccountsEnabled = false),
            DataTest.clientImport()
        )
        val users = listOf(
            DataTest.userImport(roles = roleIds),
            DataTest.userImport()
        )

        val realm = DataTest.realmImport(
            clients = clients,
            roles = roles,
            users = users
        )

        val command = ImportDataCommand(
            realmImport = realm,
            auth = masterClient.auth
        )

        val importResult = importDataFunction.invokeSingle(command)

        AssertionKC.realm(masterClient.keycloak).exist(realm.id)

        val roleAssertion = AssertionKC.role(masterClient.keycloak)
        roles.forEach { role ->
            roleAssertion.assertThat(realm.id, role.name).matchImport(role)
        }

        val clientAssertion = AssertionKC.client(masterClient.keycloak)
        val clientPerIdentifiers = clients.associateBy(ClientImport::clientIdentifier)
        importResult.clientIdPerIdentifiers.forEach { (clientId, clientIdentifier) ->
            val client = clientPerIdentifiers[clientIdentifier]!!
            clientAssertion.assertThat(realm.id, clientId).matchImport(client)
        }

        val userAssertion = AssertionKC.user(masterClient.keycloak)
        val userPerUsernames = users.associateBy(UserImport::username)
        importResult.userIdPerUsernames.forEach { (userId, username) ->
            val user = userPerUsernames[username]!!
            userAssertion.assertThat(realm.id, userId).matchImport(user)
        }
    }
}