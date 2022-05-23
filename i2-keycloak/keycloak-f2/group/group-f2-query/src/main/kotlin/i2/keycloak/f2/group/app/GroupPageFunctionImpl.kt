package i2.keycloak.f2.group.app

import f2.dsl.cqrs.page.Page
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.group.app.model.asModel
import i2.keycloak.f2.group.domain.features.query.GroupPageFunction
import i2.keycloak.f2.group.domain.features.query.GroupPageResult
import i2.keycloak.realm.client.config.AuthRealmClient
import javax.ws.rs.NotFoundException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger

@Configuration
class GroupPageFunctionImpl {

	private val logger by Logger()

	@Bean
	fun groupPageFunction(): GroupPageFunction = keycloakF2Function { cmd, client ->
		try {
			val roles = client.roles().list().associate { role ->
				val composites = client.getRoleResource(role.name).realmRoleComposites.mapNotNull { it.name }
				role.name to composites.toList()
			}

			var groups = client.groups(cmd.realmId).groups("", 0, client.countGroups(cmd.realmId), false)

			cmd.name?.let {
				groups = groups.filter { group -> group.name.contains(it, true) }
			}

			cmd.role?.let {
				groups = groups.filter { group -> group.realmRoles.contains(it) }
			}

			val count = groups.count()

			if (cmd.page.page != null && cmd.page.size != null) {
				groups = groups.chunked(cmd.page.size!!).getOrNull(cmd.page.page!!).orEmpty()
			}

			GroupPageResult(
				page = Page(
					total = count,
					items = groups.map { group -> group.asModel{ roles[it].orEmpty().toList() } }
				)
			)
		} catch (e: NotFoundException) {
			GroupPageResult(Page(0, emptyList()))
		} catch (e: Exception) {
			val msg = "Error fetching Groups"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception()
		}
	}

	private fun AuthRealmClient.countGroups(realmId: String): Int {
		return this.groups(realmId).count()["count"]!!.toInt()
	}
}
