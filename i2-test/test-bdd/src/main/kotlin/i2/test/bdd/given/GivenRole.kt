package i2.test.bdd.given

import f2.function.spring.invokeSingle
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.s2.role.domain.RoleId
import i2.s2.role.domain.features.command.RoleCreateCommand
import i2.s2.role.f2.RoleCreateFunctionImpl
import kotlinx.coroutines.runBlocking

class GivenRole(
	val client: AuthRealmClient
) {
	fun withRole(roleId: RoleId, composite: List<RoleId> = emptyList()): RoleId = runBlocking {
		val cmd = RoleCreateCommand(
			id = roleId,
			description = "description",
			isClientRole = false,
			composite = composite,
			auth = client.auth
		)
		RoleCreateFunctionImpl().roleCreateFunction().invokeSingle(cmd).id
	}
}

fun GivenKC.role() = GivenRole(client)