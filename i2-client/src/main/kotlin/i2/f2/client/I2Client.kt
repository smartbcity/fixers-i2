package i2.f2.client

import i2.f2.organization.domain.features.command.OrganizationCreateCommand
import i2.f2.organization.domain.features.command.OrganizationCreatedResult
import i2.f2.organization.domain.features.query.OrganizationGetByIdQuery
import i2.f2.organization.domain.features.query.OrganizationGetByIdQueryResult
import i2.f2.organization.domain.features.query.OrganizationRefGetAllQuery
import i2.f2.organization.domain.features.query.OrganizationRefGetAllQueryResult
import i2.f2.role.domain.features.command.RoleCreateCommand
import i2.f2.role.domain.features.command.RoleCreatedResult
import i2.f2.role.domain.features.command.RoleUpdateCommand
import i2.f2.role.domain.features.command.RoleUpdatedResult

class I2Client(
    url: String
): Client(url) {
    suspend fun createRole(command: List<RoleCreateCommand>, token: String):
            List<RoleCreatedResult> = postWithBearerToken("roleCreate", command, token)

    suspend fun updateRole(command: List<RoleUpdateCommand>, token: String):
            List<RoleUpdatedResult> = postWithBearerToken("roleUpdate", command, token)

    suspend fun createOrganization(command: List<OrganizationCreateCommand>, token: String):
            List<OrganizationCreatedResult> = postWithBearerToken("createOrganization", command, token)

    suspend fun getOrganization(command: List<OrganizationGetByIdQuery>, token: String):
            List<OrganizationGetByIdQueryResult> = postWithBearerToken("getOrganization", command, token)

    suspend fun getOrganizationRef(command: List<OrganizationRefGetAllQuery>, token: String):
            List<OrganizationRefGetAllQueryResult> = postWithBearerToken("getAllOrganizationRefs", command, token)
}
