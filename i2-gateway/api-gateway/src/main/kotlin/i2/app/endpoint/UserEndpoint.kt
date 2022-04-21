package i2.app.endpoint

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.app.auth.PermissionEvaluator
import i2.app.auth.SUPER_ADMIN_ROLE
import i2.f2.user.domain.features.command.UserCreateFunction
import i2.f2.user.domain.features.command.UserResetPasswordFunction
import i2.f2.user.domain.features.command.UserUpdateFunction
import i2.f2.user.domain.features.query.UserGetAllQueryFunction
import i2.f2.user.domain.features.query.UserGetByIdQuery
import i2.f2.user.domain.features.query.UserGetByIdQueryFunction
import javax.annotation.security.RolesAllowed
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserEndpoint(
    private val userCreateFunction: UserCreateFunction,
    private val userResetPasswordFunction: UserResetPasswordFunction,
    private val userUpdateFunction: UserUpdateFunction,
    private val userGetByIdQueryFunction: UserGetByIdQueryFunction,
    private val userGetAllQueryFunction: UserGetAllQueryFunction,
    private val permissionEvaluator: PermissionEvaluator
) {

    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE, "write_user")
    fun createUser(): UserCreateFunction = f2Function { cmd ->
        if (permissionEvaluator.isSuperAdmin() || permissionEvaluator.checkOrganizationId(cmd.memberOf)) {
            userCreateFunction.invoke(cmd)
        } else {
            throw IllegalAccessException("Access denied.")
        }
    }

    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE, "write_user")
    fun updateUser(): UserUpdateFunction = f2Function { cmd ->
        if (permissionEvaluator.isSuperAdmin() || permissionEvaluator.checkOrganizationId(cmd.memberOf)) {
            userUpdateFunction.invoke(cmd)
        } else {
            throw IllegalAccessException("Access denied.")
        }
    }

    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE, "write_user")
    fun resetUserPassword(): UserResetPasswordFunction = f2Function { cmd ->
        val user = userGetByIdQueryFunction.invoke(UserGetByIdQuery(cmd.id)).user
        if (permissionEvaluator.isSuperAdmin() || permissionEvaluator.checkOrganizationId(user?.memberOf?.id)) {
            userResetPasswordFunction.invoke(cmd)
        } else {
            throw IllegalAccessException("Access denied.")
        }
    }

    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE, "read_user")
    fun getUser() = userGetByIdQueryFunction

    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE, "read_user")
    fun getAllUsers() = userGetAllQueryFunction
}
