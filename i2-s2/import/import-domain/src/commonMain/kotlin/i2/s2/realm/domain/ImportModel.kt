package i2.s2.realm.domain

import i2.s2.role.domain.features.command.RoleCreateCommand


class RealmImport(
	val realmName: String,
	val roles: List<RoleImport>,
	val users: List<UserImport>
)

class RoleImport(
	val role: RoleCreateCommand,
)
class UserImport(
	val user: RoleCreateCommand,
)