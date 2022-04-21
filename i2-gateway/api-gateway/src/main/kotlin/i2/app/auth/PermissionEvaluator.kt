package i2.app.auth

import i2.app.auth.AuthenticationProvider.hasRole
import i2.f2.organization.domain.model.OrganizationId
import org.springframework.stereotype.Service

const val ROLE_PREFIX = "ROLE_"
const val SUPER_ADMIN_ROLE = "admin"

@Service
class PermissionEvaluator{
    suspend fun isSuperAdmin(): Boolean {
        return hasRole(ROLE_PREFIX + SUPER_ADMIN_ROLE)
    }

    suspend fun checkOrganizationId(organizationId: OrganizationId?): Boolean {
        if (organizationId == null) return false
        return AuthenticationProvider.getOrganizationId() == organizationId
    }
}