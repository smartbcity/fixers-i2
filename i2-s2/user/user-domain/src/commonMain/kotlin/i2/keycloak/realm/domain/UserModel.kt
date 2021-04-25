package i2.keycloak.realm.domain

import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserId = String

@JsExport
@JsName("UserDomain")
class UserDomain(
	val id: UserId,
	val email: String
)