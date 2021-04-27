package i2.test.bdd.assertion

import i2.keycloak.realm.domain.UserId
import org.assertj.core.api.Assertions
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.UserRepresentation


fun AssertionKC.Companion.user(keycloak: Keycloak): AssertionUser = AssertionUser(keycloak)

class AssertionUser(
	private val keycloak: Keycloak,
) {
	companion object

	fun exist(realmId: String, id: UserId) {
		try {
			val user = getUserRepresentation(realmId, id)
			Assertions.assertThat(user).isNotNull
		} catch (e: javax.ws.rs.NotFoundException) {
			Assertions.fail("User[${id} not found]", e)
		}
	}


	fun isDisabled(realmId: String, id: UserId) {
		try {
			val user = getUserRepresentation(realmId, id)
			Assertions.assertThat(user.isEnabled).isFalse()
		} catch (e: javax.ws.rs.NotFoundException) {
			Assertions.fail("Realm[${id} not found]", e)
		}
	}


	fun notExist(realmId: String, id: UserId) {
		try {
			getUserRepresentation(realmId, id)
			Assertions.fail("Realm[${id} exist]")
		} catch (e: javax.ws.rs.NotFoundException) {
			Assertions.assertThat(true).isTrue
		}
	}

	fun assertThat(realmId: String, id: UserId): UserComparator {
		exist(realmId, id)
		val user = getUserRepresentation(realmId, id)
		return UserComparator(user)
	}

	private fun getUserRepresentation(
		realmId: String,
		id: String,
	): UserRepresentation {
		return keycloak.realm(realmId).users().get(id).toRepresentation()
	}

	inner class UserComparator(
		private val user: UserRepresentation
	) {
		fun hasFields(
			userId: UserId = user.id,
			username: String = user.username,
			firstname: String? = user.firstName,
			lastname: String? = user.lastName,
			email: String = user.email,
			isEnable: Boolean = user.isEnabled,
			metadata: Map<String, String> = emptyMap()
		) {
			Assertions.assertThat(user.id).isEqualTo(userId)
			Assertions.assertThat(user.username).isEqualTo(username)
			Assertions.assertThat(user.firstName).isEqualTo(firstname)
			Assertions.assertThat(user.lastName).isEqualTo(lastname)
			Assertions.assertThat(user.email).isEqualTo(email)
			Assertions.assertThat(user.isEnabled).isEqualTo(isEnable)
			metadata.forEach { (key, value) ->
				Assertions.assertThat(user.attributes[key]).isEqualTo(listOf(value))
			}
		}
	}
}