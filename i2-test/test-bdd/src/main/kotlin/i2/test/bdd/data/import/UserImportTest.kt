package i2.test.bdd.data.import

import i2.s2.realm.domain.UserImport
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.Companion.userImport(
    username: String = UUID.randomUUID().toString(),
    firstname: String? = null,
    lastname: String? = null,
    email: String = "$username@email.com",
    isEnable: Boolean = true,
    metadata: Map<String, String> = emptyMap(),
    roles: List<String> = emptyList()
) = UserImport(
    username = username,
    firstname = firstname,
    lastname = lastname,
    email = email,
    isEnable = isEnable,
    metadata = metadata,
    roles = roles
)