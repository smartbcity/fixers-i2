package i2.keycloak.master.domain

sealed class AuthRealm(
	open val serverUrl: String,
	open val realm: String,
	open val clientId: String,
	open val redirectUrl: String,
)

class AuthRealmPassword(
	override val serverUrl: String,
	override val realm: String,
	override val clientId: String,
	override val redirectUrl: String,
	val username: String,
	val password: String,
) : AuthRealm(serverUrl, realm, clientId, redirectUrl)

class AuthRealmClientSecret(
	override val serverUrl: String,
	override val realm: String,
	override val clientId: String,
	override val redirectUrl: String,
	val clientSecret: String,

) : AuthRealm(serverUrl, realm, clientId, redirectUrl)