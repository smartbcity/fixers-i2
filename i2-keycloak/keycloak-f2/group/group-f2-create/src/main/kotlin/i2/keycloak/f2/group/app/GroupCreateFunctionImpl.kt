package i2.keycloak.f2.group.app

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.group.domain.features.command.GroupCreateCommand
import i2.keycloak.f2.group.domain.features.command.GroupCreateFunction
import i2.keycloak.f2.group.domain.features.command.GroupCreatedResult
import i2.keycloak.f2.group.domain.model.GroupId
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.realmsResource
import i2.keycloak.utils.handleResponseError
import i2.keycloak.utils.isFailure
import i2.keycloak.utils.onCreationFailure
import i2.keycloak.utils.toEntityCreatedId
import org.keycloak.representations.idm.GroupRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.ws.rs.core.Response


@Configuration
class GroupCreateFunctionImpl {

	@Bean
	fun groupCreateFunction(): GroupCreateFunction = keycloakF2Function { cmd, client ->
		val groupId = cmd.parentGroupId?.let { parentGroup ->
			client.createSubGroup(parentGroup, cmd)
		} ?: client.createGroup(cmd)

		client.addRolesToGroup(groupId, cmd)

		GroupCreatedResult(groupId)
	}

	private fun AuthRealmClient.createSubGroup(parentGroup: GroupId, cmd: GroupCreateCommand): GroupId {
		return toGroupRepresentation(cmd).let { group ->
			realmsResource(cmd.realmId).groups().group(parentGroup).subGroup(group)
		}.handleResponseError("group")
	}


	private fun AuthRealmClient.createGroup(cmd: GroupCreateCommand): GroupId {
		return toGroupRepresentation(cmd)
			.let(
				realmsResource(cmd.realmId).groups()::add
			).handleResponseError("group")
	}

	private fun toGroupRepresentation(cmd: GroupCreateCommand): GroupRepresentation {
		return GroupRepresentation().apply {
			name = cmd.name
			attributes = cmd.attributes
		}
	}

	private fun AuthRealmClient.addRolesToGroup(groupId: GroupId, cmd: GroupCreateCommand) {
		val roles = cmd.roles.map { role ->
			realm.roles()[role].toRepresentation()
		}

		this.getGroupResource(cmd.realmId, groupId).roles().realmLevel().add(roles)
	}
}
