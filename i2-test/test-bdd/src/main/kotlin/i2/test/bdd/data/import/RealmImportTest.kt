package i2.test.bdd.data.import

import i2.f2.import.domain.ClientImport
import i2.f2.import.domain.RealmImport
import i2.f2.import.domain.RoleImport
import i2.f2.import.domain.UserImport
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.realmImport(
    id: String = UUID.randomUUID().toString(),
    theme: String? = null,
    locale: String? = null,
    smtpServer: Map<String, String>? = emptyMap(),
    roles: List<RoleImport> = emptyList(),
    clients: List<ClientImport> = emptyList(),
    users: List<UserImport> = emptyList()
) = RealmImport(
    id = id,
    theme = theme,
    locale = locale,
    smtpServer = smtpServer,
    roles = roles,
    clients = clients,
    users = users
)
