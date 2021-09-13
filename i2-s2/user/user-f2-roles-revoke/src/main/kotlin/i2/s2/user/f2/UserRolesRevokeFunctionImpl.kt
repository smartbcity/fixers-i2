package i2.s2.user.f2

import f2.dsl.fnc.f2Function
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.keycloak.realm.domain.UserId
import i2.keycloak.realm.domain.features.command.UserRolesRevokeFunction
import i2.keycloak.realm.domain.features.command.UserRolesRevokedResult
import org.keycloak.admin.client.resource.RoleScopeResource
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserRolesRevokeFunctionImpl {

	@Bean
	fun userRolesRevokeFunction(): UserRolesRevokeFunction = f2Function { cmd ->
		val realmClient = AuthRealmClientBuilder().build(cmd.auth)
		realmClient.removeUserRole(cmd.id, cmd.roles)
		UserRolesRevokedResult(cmd.id)
	}

	fun AuthRealmClient.removeUserRole(userId: UserId, roles: List<String>) {
		val roleRepresentations = roles.map { role ->
			getRoleRepresentation(role)
		}
		getUserRealmRolesResource(userId).remove(roleRepresentations)
	}

	fun AuthRealmClient.getRoleRepresentation(role: String): RoleRepresentation {
		return realm.roles().get(role).toRepresentation()
	}

	private fun AuthRealmClient.getUserRealmRolesResource(userId: String): RoleScopeResource {
		return getUserResource(userId).roles().realmLevel()
	}
}
