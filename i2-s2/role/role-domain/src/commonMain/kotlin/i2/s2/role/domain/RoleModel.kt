package i2.s2.role.domain

import kotlin.js.JsExport
import kotlin.js.JsName


typealias RoleId = String
typealias RoleName = String

@JsExport
@JsName("RoleModel")
class RoleModel(
	val id: RoleId,
	val name: RoleName,
	val description: String,
	val isClientRole: Boolean,
)
