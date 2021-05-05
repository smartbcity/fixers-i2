package i2.s2.user.f2

import f2.function.spring.adapter.f2Function
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.keycloak.realm.domain.UserId
import i2.keycloak.realm.domain.features.command.UserRolesGrantFunction
import i2.keycloak.realm.domain.features.command.UserRolesGrantedResult
import org.keycloak.admin.client.resource.RoleScopeResource
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserRolesGrantFunctionImpl {

	@Bean
	fun userRolesGrantFunction(): UserRolesGrantFunction = f2Function { cmd ->
		val realmClient = AuthRealmClientBuilder().build(cmd.auth)
		realmClient.addUserRole(cmd.realmId, cmd.id, cmd.roles)
		UserRolesGrantedResult(cmd.id)
	}

	fun AuthRealmClient.addUserRole(realmId: RealmId, userId: UserId, roles: List<String>) {
		val roleRepresentations = roles.map { role ->
			getRoleRepresentation(realmId, role)
		}
		getUserRealmRolesResource(realmId, userId).add(roleRepresentations)
	}

	fun AuthRealmClient.getRoleRepresentation(realmId: RealmId, role: String): RoleRepresentation {
		return getRoleResource(realmId, role).toRepresentation()
	}

	protected fun AuthRealmClient.getUserRealmRolesResource(realmId: RealmId, userId: String): RoleScopeResource {
		return getUserResource(realmId, userId).roles().realmLevel()
	}

}