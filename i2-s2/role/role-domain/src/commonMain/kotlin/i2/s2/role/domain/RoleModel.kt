package i2.s2.role.domain

import kotlin.js.JsExport
import kotlin.js.JsName


typealias RoleId = String

@JsExport
@JsName("RoleModel")
class RoleModel(
	val id: RoleId,
	val description: String,
	val isClientRole: Boolean,
)