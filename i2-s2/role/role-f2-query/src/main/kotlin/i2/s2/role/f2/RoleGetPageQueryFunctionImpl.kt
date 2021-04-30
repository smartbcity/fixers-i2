package i2.s2.role.f2

import f2.dsl.cqrs.base.PageBase
import f2.function.spring.adapter.f2Function
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.s2.role.domain.RoleModel
import i2.s2.role.domain.features.query.RoleGetPageQueryFunction
import i2.s2.role.domain.features.query.RoleGetPageQueryResult
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoleGetPageQueryFunctionImpl {

	@Bean
	fun roleGetPageQueryFunctionImpl(): RoleGetPageQueryFunction = f2Function { cmd ->
		val realmClient = AuthRealmClientBuilder().build(cmd.auth)

		val size = cmd.page.size ?: 10
		val page = cmd.page.page ?: 1
		val roles = realmClient.roles(cmd.realmId).list()

		roles.chunked(size)
			.getOrElse(page) { emptyList() }
			.asModels()
			.asResult(page, size, roles.size)
	}


	private fun List<RoleModel>.asResult(page: Int, size: Int, total: Int): RoleGetPageQueryResult {
		return RoleGetPageQueryResult(PageBase(
			page = page,
			size = size,
			total = total.toLong(),
			list = this
		))
	}

}