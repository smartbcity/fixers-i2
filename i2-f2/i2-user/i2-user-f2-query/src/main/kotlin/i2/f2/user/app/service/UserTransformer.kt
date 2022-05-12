package i2.f2.user.app.service

import f2.dsl.fnc.invokeWith
import i2.commons.model.AddressBase
import i2.commons.utils.parseJsonTo
import i2.f2.config.I2KeycloakConfig
import i2.f2.organization.app.model.orEmpty
import i2.f2.organization.domain.model.OrganizationRef
import i2.f2.user.domain.model.UserBase
import i2.keycloak.f2.user.domain.features.query.UserGetGroupsFunction
import i2.keycloak.f2.user.domain.features.query.UserGetGroupsQuery
import i2.keycloak.f2.user.domain.model.UserModel

class UserTransformer(
    private val i2KeycloakConfig: I2KeycloakConfig,
    private val userGetGroupsQueryFunction: UserGetGroupsFunction,

    ) {

	suspend fun toUser(user: UserModel): UserBase {
		val group = UserGetGroupsQuery(
			userId = user.id,
			realmId =  i2KeycloakConfig.realm,
			auth = i2KeycloakConfig.authRealm()
		).invokeWith(userGetGroupsQueryFunction).items.firstOrNull()?.let { group ->
			OrganizationRef(
				id = group.id,
				name = group.name,
				roles = group.roles
			)
		}
		return UserBase(
			id = user.id,
			memberOf = group,
			email = user.email ?: "",
			givenName = user.firstName ?: "",
			familyName = user.lastName ?: "",
			address = user.attributes[UserBase::address.name]?.first()?.parseJsonTo(AddressBase::class.java).orEmpty(),
			phone = user.attributes[UserBase::phone.name]?.firstOrNull(),
			roles = user.roles,
			sendEmailLink = user.attributes[UserBase::sendEmailLink.name]?.firstOrNull().toBoolean()
		)
	}
	
}
