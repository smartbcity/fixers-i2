package i2.app.endpoint

import i2.f2.user.domain.features.command.UserCreateFunction
import i2.f2.user.domain.features.command.UserUpdateFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserEndpoint(
    private val userCreateFunction: UserCreateFunction,
    private val userUpdateFunction: UserUpdateFunction
) {

    @Bean
    fun createUser() = userCreateFunction

    @Bean
    fun updateUser() = userUpdateFunction
}
