package i2.app.endpoint

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import i2.app.auth.PermissionEvaluator
import i2.f2.user.domain.features.command.UserCreateFunction
import i2.f2.user.domain.features.command.UserResetPasswordFunction
import i2.f2.user.domain.features.command.UserUpdateFunction
import i2.f2.user.domain.features.query.UserGetFunction
import i2.f2.user.domain.features.query.UserGetQuery
import i2.f2.user.domain.features.query.UserPageFunction
import javax.annotation.security.RolesAllowed
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @d2 service
 * @title User/Entrypoints
 */
@Configuration
class UserEndpoint(
    private val userCreateFunction: UserCreateFunction,
    private val userResetPasswordFunction: UserResetPasswordFunction,
    private val userUpdateFunction: UserUpdateFunction,
    private val userGetFunction: UserGetFunction,
    private val userPageFunction: UserPageFunction,
    private val permissionEvaluator: PermissionEvaluator
) {

    /**
     * Creates a User.
     */
    @Bean
    @RolesAllowed("write_user")
    fun userCreate(): UserCreateFunction = f2Function { cmd ->
        if (permissionEvaluator.isSuperAdmin() || permissionEvaluator.checkOrganizationId(cmd.memberOf)) {
            userCreateFunction.invoke(cmd)
        } else {
            throw IllegalAccessException("Access denied.")
        }
    }

    /**
     * Updates a User.
     */
    @Bean
    @RolesAllowed("write_user")
    fun userUpdate(): UserUpdateFunction = f2Function { cmd ->
        if (permissionEvaluator.isSuperAdmin() || permissionEvaluator.checkOrganizationId(cmd.memberOf)) {
            userUpdateFunction.invoke(cmd)
        } else {
            throw IllegalAccessException("Access denied.")
        }
    }

    /**
     * Sets the given password for the given user ID.
     */
    @Bean
    @RolesAllowed("write_user")
    fun userResetPassword(): UserResetPasswordFunction = f2Function { cmd ->
        val user = userGetFunction.invoke(UserGetQuery(cmd.id)).item
        if (permissionEvaluator.isSuperAdmin() || permissionEvaluator.checkOrganizationId(user?.memberOf?.id)) {
            userResetPasswordFunction.invoke(cmd)
        } else {
            throw IllegalAccessException("Access denied.")
        }
    }

    /**
     * Fetches a User by its ID.
     */
    @Bean
    @RolesAllowed("read_user")
    fun userGet() = userGetFunction

    /**
     * Fetches a page of users.
     */
    @Bean
    @RolesAllowed("read_user")
    fun userPage() = userPageFunction
}
