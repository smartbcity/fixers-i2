package i2.s2.user.f2

import i2.keycloak.realm.domain.features.query.UserGetRolesQueryFunction
import i2.keycloak.realm.domain.features.query.UserGetRolesQueryResult
import i2.s2.commons.f2.keycloakF2Function
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserGetRolesQueryFunctionImpl {

	@Bean
	fun userGetRolesQueryFunctionImpl(): UserGetRolesQueryFunction = keycloakF2Function { cmd, realmClient ->
		realmClient.keycloak.realm(cmd.realmId).users().get(cmd.userId).roles().realmLevel().listEffective()
			.map(RoleRepresentation::getName)
			.asResult()
	}

	private fun List<String>.asResult(): UserGetRolesQueryResult {
		return UserGetRolesQueryResult(
			roles = this
		)
	}
}
