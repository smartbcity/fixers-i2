package i2.test.bdd.assertion

import i2.keycloak.master.domain.RealmId
import i2.s2.role.domain.RoleId
import org.assertj.core.api.Assertions
import org.keycloak.admin.client.Keycloak
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
		val role = getRoleRepresentation(realmId, id)
		return RoleAssert(role)
	}

	private fun getRoleRepresentation(realmId: String, id: String): RoleRepresentation {
		return keycloak.realm(realmId).roles().get(id).toRepresentation()
	}

	inner class RoleAssert(
		private val role: RoleRepresentation
	) {
		fun hasFields(
			id: RoleId = role.id,
			description: String? = role.description,
			isClientRole: Boolean = role.clientRole,
			composites: Iterable<RoleId> = role.composites?.realm ?: emptyList(),
		) {
			Assertions.assertThat(id).isEqualTo(role.id)
			Assertions.assertThat(description).isEqualTo(role.description)
			Assertions.assertThat(isClientRole).isEqualTo(role.clientRole)
			if (composites.count() > 0) {
				Assertions.assertThat(role.composites).isNotNull
				Assertions.assertThat(composites).containsAll(role.composites.realm)
			} else {
				Assertions.assertThat(role.composites?.realm).isNullOrEmpty()
			}
		}
	}
}