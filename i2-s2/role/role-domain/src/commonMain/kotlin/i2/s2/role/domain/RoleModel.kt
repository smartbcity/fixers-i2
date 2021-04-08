package i2.s2.role.domain


typealias RoleId = String

class RoleModel(
	val id: RoleId,
	val description: String,
	val isClientRole: Boolean,
)