package i2.test.bdd.data.role

import i2.keycloak.master.domain.AuthRealm
import i2.s2.role.domain.RoleId
import i2.s2.role.domain.features.command.RoleCreateCommand
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.Companion.roleCreateCommand(
    auth: AuthRealm,
    id: RoleId = UUID.randomUUID().toString(),
    description: String? = null,
    isClientRole: Boolean = false,
    composites: List<RoleId> = emptyList(),
) = RoleCreateCommand(
    id = id,
    description = description,
    isClientRole = isClientRole,
    composites = composites,
    auth = auth
)