package i2.test.bdd.data.user

import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.realm.domain.UserId
import i2.keycloak.realm.domain.features.command.UserUpdateCommand
import i2.s2.realm.domain.RealmId
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.Companion.userUpdateCommand(
    realmId: RealmId,
    auth: AuthRealm,
    userId: UserId = UUID.randomUUID().toString(),
    firstname: String? = null,
    lastname: String? = null,
    email: String = "$userId@mail.com",
    metadata: Map<String, String> = emptyMap(),
) = UserUpdateCommand(
    userId = userId,
    realmId = realmId,
    firstname = firstname,
    lastname = lastname,
    email = email,
    metadata = metadata,
    auth = auth,
)