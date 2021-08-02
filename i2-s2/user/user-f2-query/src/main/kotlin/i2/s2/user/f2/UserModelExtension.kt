package i2.s2.user.f2

import i2.keycloak.realm.domain.UserModel
import org.keycloak.representations.idm.UserRepresentation

fun List<UserRepresentation>.asModels(): List<UserModel>  = map(UserRepresentation::asModel)

fun UserRepresentation.asModel(): UserModel {
	return UserModel(
		id = this.id,
		email = this.email,
	)
}