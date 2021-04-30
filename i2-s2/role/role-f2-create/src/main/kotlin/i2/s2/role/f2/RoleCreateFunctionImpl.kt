package i2.s2.role.f2

import f2.function.spring.adapter.f2Function
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.s2.role.domain.RoleId
import i2.s2.role.domain.features.command.RoleCreateCommand
import i2.s2.role.domain.features.command.RoleCreateFunction
import i2.s2.role.domain.features.command.RoleCreatedResult
import org.keycloak.admin.client.resource.RoleResource
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoleCreateFunctionImpl {

	@Bean
	fun roleCreateFunction(): RoleCreateFunction = f2Function { cmd ->
		val realmClient = AuthRealmClientBuilder().build(cmd.auth)
		realmClient.initRole(cmd)
		RoleCreatedResult(cmd.id)
	}

	fun AuthRealmClient.initRole(cmd: RoleCreateCommand) {
		val compositeRoles = cmd.composites.map { role ->
			getRoleRepresentation(role)
		}
		createRole(cmd.id, cmd.description, cmd.isClientRole)
		getRolesResource(cmd.id).addComposites(compositeRoles)
	}

	fun AuthRealmClient.getRoleRepresentation(role: RoleId): RoleRepresentation {
		return realm.roles().get(role).toRepresentation()
	}

	protected fun AuthRealmClient.getRolesResource(roleId: RoleId): RoleResource {
		return this.realm.roles().get(roleId)
	}

	private fun AuthRealmClient.createRole(role: RoleId, description: String?, isClientRole: Boolean): RoleRepresentation {
		return RoleRepresentation().apply {
			this.name = role
			this.description = description
			this.clientRole = isClientRole
		}.also { roleRepresentation ->
			realm.roles().create(roleRepresentation)
		}
	}
}
