package i2.test.bdd.data.role

import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import i2.s2.role.domain.RoleId
import i2.s2.role.domain.features.command.RoleAddCompositesCommand
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.Companion.roleAddCompositesCommand(
    auth: AuthRealm,
    realmId: RealmId,
    roleId: RoleId = UUID.randomUUID().toString(),
    composites: List<RoleId> = emptyList()
) = RoleAddCompositesCommand(
    roleId = roleId,
    composites = composites,
    auth = auth,
    realmId = realmId
)
