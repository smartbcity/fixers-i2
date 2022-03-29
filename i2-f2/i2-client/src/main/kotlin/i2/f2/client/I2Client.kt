package i2.f2.client

import i2.f2.role.domain.features.command.RoleCreateCommand
import i2.f2.role.domain.features.command.RoleCreatedResult

class I2Client(
    url: String
): Client(url) {
    suspend fun createRole(command: List<RoleCreateCommand>): List<RoleCreatedResult> = post("roleCreate", command)
}
