package i2.keycloak.realm.domain

import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserId = String

@JsExport
@JsName("UserModel")
class UserModel(
	val id: UserId,
	val email: String?
)