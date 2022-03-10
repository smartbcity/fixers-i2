package i2.test.bdd.data.import

import i2.f2.import.domain.RoleImport
import i2.keycloak.f2.role.domain.RoleName
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.roleImport(
    name: RoleName = UUID.randomUUID().toString(),
    description: String? = null,
    isClientRole: Boolean = false,
    composites: List<RoleName> = emptyList(),
) = RoleImport(
    name = name,
    description = description,
    isClientRole = isClientRole,
    composites = composites
)
