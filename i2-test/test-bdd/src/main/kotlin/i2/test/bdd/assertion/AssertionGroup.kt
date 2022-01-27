package i2.test.bdd.assertion

import i2.s2.group.domain.model.GroupId
import org.assertj.core.api.Assertions
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.GroupRepresentation


fun AssertionKC.group(keycloak: Keycloak): AssertionGroup = AssertionGroup(keycloak)

class AssertionGroup(
	private val keycloak: Keycloak,
) {
	companion object

	fun exists(realmId: String, id: GroupId) {
		try {
			val group = getGroupRepresentation(realmId, id)
			Assertions.assertThat(group).isNotNull
		} catch (e: javax.ws.rs.NotFoundException) {
			Assertions.fail("Group[${id}] not found", e)
		}
	}

	fun notExists(realmId: String, id: GroupId) {
		try {
			getGroupRepresentation(realmId, id)
			Assertions.fail("Group[${id}] exists")
		} catch (e: javax.ws.rs.NotFoundException) {
			Assertions.assertThat(true).isTrue
		}
	}

	fun assertThat(realmId: String, id: GroupId): GroupAssert {
		exists(realmId, id)
		val group = getGroupRepresentation(realmId, id)
		return GroupAssert(group)
	}

	private fun getGroupRepresentation(realmId: String, id: String): GroupRepresentation {
		return keycloak.realm(realmId).groups().group(id).toRepresentation()
	}

	inner class GroupAssert(
		private val group: GroupRepresentation
	) {
		fun hasFields(
			id: GroupId = group.id,
			name: String = group.name,
			realmRoles: List<String> = group.realmRoles,
			attributes: Map<String, List<String>> = group.attributes
		) {
			Assertions.assertThat(group.id).isEqualTo(id)
			Assertions.assertThat(group.name).isEqualTo(name)
			Assertions.assertThat(group.realmRoles).isEqualTo(realmRoles)
			attributes.forEach { (key, value) ->
				Assertions.assertThat(group.attributes[key]).isEqualTo(value)
			}
		}
	}
}
