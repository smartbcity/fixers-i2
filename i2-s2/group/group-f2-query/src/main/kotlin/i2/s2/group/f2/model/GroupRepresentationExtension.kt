package i2.s2.group.f2.model

import i2.s2.group.domain.model.GroupModel
import org.keycloak.representations.idm.GroupRepresentation

fun GroupRepresentation.asModel() = GroupModel(
    id = id,
    name = name,
    attributes = attributes,
    roles = realmRoles
)
