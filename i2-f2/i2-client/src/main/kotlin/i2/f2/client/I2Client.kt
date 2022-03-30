package i2.f2.client

import i2.f2.role.domain.features.command.RoleCreateCommand
import i2.f2.role.domain.features.command.RoleCreatedResult
import i2.f2.role.domain.features.command.RoleUpdateCommand
import i2.f2.role.domain.features.command.RoleUpdatedResult

class I2Client(
    url: String
): Client(url) {
    suspend fun createRole(command: List<RoleCreateCommand>): List<RoleCreatedResult> = post("roleCreate", command)
    suspend fun updateRole(command: List<RoleUpdateCommand>): List<RoleUpdatedResult> = post("roleUpdate", command)
}
