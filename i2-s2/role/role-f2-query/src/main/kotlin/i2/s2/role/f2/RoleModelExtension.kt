package i2.s2.role.f2

import i2.s2.role.domain.RoleModel
import org.keycloak.representations.idm.RoleRepresentation

fun RoleRepresentation.asModel() = RoleModel(
    id = id,
    description = description,
    isClientRole = clientRole
)