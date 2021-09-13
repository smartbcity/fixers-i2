package i2.test.bdd.data.user

import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.realm.domain.features.command.UserCreateCommand
import i2.s2.realm.domain.RealmId
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.userCreateCommand(
    realmId: RealmId,
    auth: AuthRealm,
    username: String = "user-{${UUID.randomUUID()}}",
    firstname: String? = null,
    lastname: String? = null,
    email: String = "$username@mail.com",
    isEnable: Boolean = true,
    metadata: Map<String, String> = emptyMap(),
) = UserCreateCommand(
     realmId = realmId,
     username = username,
     firstname = firstname,
     lastname = lastname,
     email = email,
     isEnable = isEnable,
     metadata = metadata,
     auth = auth,
)
