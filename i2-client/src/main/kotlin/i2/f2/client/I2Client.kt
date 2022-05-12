package i2.f2.client

import i2.f2.organization.domain.features.command.OrganizationCreateCommand
import i2.f2.organization.domain.features.command.OrganizationCreateResult
import i2.f2.organization.domain.features.query.OrganizationGetQuery
import i2.f2.organization.domain.features.query.OrganizationGetResult
import i2.f2.organization.domain.features.query.OrganizationRefGetAllQuery
import i2.f2.organization.domain.features.query.OrganizationRefGetAllResult
import i2.f2.role.domain.features.command.RoleCreateCommand
import i2.f2.role.domain.features.command.RoleCreateResult
import i2.f2.role.domain.features.command.RoleUpdateCommand
import i2.f2.role.domain.features.command.RoleUpdateResult

class I2Client(
    url: String
): Client(url) {
    suspend fun roleCreate(command: List<RoleCreateCommand>, token: String):
            List<RoleCreateResult> = post("roleCreate", command, token)

    suspend fun roleUpdate(command: List<RoleUpdateCommand>, token: String):
            List<RoleUpdateResult> = post("roleUpdate", command, token)

    suspend fun organizationCreate(command: List<OrganizationCreateCommand>, token: String):
            List<OrganizationCreateResult> = post("organizationCreate", command, token)

    suspend fun organizationGet(command: List<OrganizationGetQuery>, token: String):
            List<OrganizationGetResult> = post("organizationGet", command, token)

    suspend fun organizationRefGetAll(command: List<OrganizationRefGetAllQuery>, token: String):
            List<OrganizationRefGetAllResult> = post("organizationRefGetAll", command, token)
}
