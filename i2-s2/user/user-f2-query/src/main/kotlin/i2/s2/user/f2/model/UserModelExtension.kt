package i2.s2.user.f2.model

import i2.keycloak.realm.domain.UserId
import i2.keycloak.realm.domain.UserModel
import org.keycloak.representations.idm.UserRepresentation

suspend fun List<UserRepresentation>.asModels(getRealmRoles: suspend (UserId) -> List<String>): List<UserModel>
		= map { user -> user.asModel(getRealmRoles) }

suspend fun UserRepresentation.asModel(getRealmRoles: suspend (UserId) -> List<String>): UserModel {
	return UserModel(
		id = id,
		email = email,
		firstName = firstName,
		lastName = lastName,
		realmRoles = getRealmRoles(id),
		attributes = attributes.orEmpty()
	)
}
