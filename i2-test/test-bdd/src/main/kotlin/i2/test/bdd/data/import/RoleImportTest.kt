package i2.test.bdd.data.import

import i2.s2.realm.domain.RoleImport
import i2.s2.role.domain.RoleName
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.Companion.roleImport(
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