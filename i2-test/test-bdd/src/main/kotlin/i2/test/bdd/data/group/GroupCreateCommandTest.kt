package i2.test.bdd.data.group

import i2.keycloak.f2.group.domain.features.command.GroupCreateCommand
import i2.keycloak.f2.realm.domain.RealmId
import i2.keycloak.master.domain.AuthRealm
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.groupCreateCommand(
    realmId: RealmId,
    auth: AuthRealm,
    name: String = "group-{${UUID.randomUUID()}}",
    roles: List<String> = emptyList(),
    attributes: Map<String, List<String>> = emptyMap(),
) = GroupCreateCommand(
    realmId = realmId,
    auth = auth,
    name = name,
    roles = roles,
    attributes = attributes
)