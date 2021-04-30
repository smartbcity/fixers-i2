package i2.test.bdd.assertion

import i2.keycloak.master.domain.RealmId
import i2.s2.role.domain.RoleId
import org.assertj.core.api.Assertions
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.resource.RoleResource
import org.keycloak.representations.idm.RoleRepresentation


fun AssertionKC.Companion.role(keycloak: Keycloak): AssertionRole = AssertionRole(keycloak)

class AssertionRole(
	private val keycloak: Keycloak,
) {
	companion object

	fun exists(realmId: RealmId, id: RoleId) {
		try {
			val role = getRoleRepresentation(realmId, id)
			Assertions.assertThat(role).isNotNull
		} catch (e: javax.ws.rs.NotFoundException) {
			Assertions.fail("Role[${id} not found]", e)
		}
	}

	fun notExists(realmId: RealmId, id: RoleId) {
		try {
			getRoleRepresentation(realmId, id)
			Assertions.fail("Role[${id} exist]")
		} catch (e: javax.ws.rs.NotFoundException) {
			Assertions.assertThat(true).isTrue
		}
	}

	fun assertThat(realmId: RealmId, id: RoleId): RoleAssert {
		exists(realmId, id)
		val roleResource = getRoleResource(realmId, id)
		return RoleAssert(
			role = roleResource.toRepresentation(),
			roleComposites = roleResource.roleComposites
		)
	}

	private fun getRoleRepresentation(realmId: String, id: RoleId): RoleRepresentation {
		return getRoleResource(realmId, id).toRepresentation()
	}

	private fun getRoleResource(realmId: RealmId, id: RoleId): RoleResource {
		return keycloak.realm(realmId).roles().get(id)
	}

	inner class RoleAssert(
		private val role: RoleRepresentation,
		private val roleComposites: Set<RoleRepresentation>
	) {
		fun hasFields(
			id: RoleId = role.id,
			description: String? = role.description,
			isClientRole: Boolean = role.clientRole,
			compositeNames: Iterable<RoleId> = role.composites?.realm ?: emptyList(),
		) {
			Assertions.assertThat(id).isEqualTo(role.id)
			Assertions.assertThat(description).isEqualTo(role.description)
			Assertions.assertThat(isClientRole).isEqualTo(role.clientRole)
			if (compositeNames.count() > 0) {
				Assertions.assertThat(roleComposites).isNotNull
				Assertions.assertThat(compositeNames).containsAll(roleComposites.map(RoleRepresentation::getName))
			} else {
				Assertions.assertThat(role.composites?.realm).isNullOrEmpty()
			}
		}
	}
}