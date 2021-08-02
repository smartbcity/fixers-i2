package i2.s2.client.f2

import f2.dsl.cqrs.base.PageBase
import f2.dsl.fnc.f2Function
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.s2.client.domain.ClientModel
import i2.s2.client.domain.features.query.ClientGetPageQueryFunction
import i2.s2.client.domain.features.query.ClientGetPageQueryResult
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientGetPageQueryFunctionImpl {

	@Bean
	fun clientGetPageQueryFunctionImpl(): ClientGetPageQueryFunction = f2Function { cmd ->
		val realmClient = AuthRealmClientBuilder().build(cmd.auth)
		val size = cmd.page.size ?: 10
		val page = cmd.page.page ?: 1
 		val clients = realmClient.clients(cmd.realmId)
			.findAll()

		clients.chunked(size)
			.getOrElse(page) { emptyList() }
			.asModels()
			.asResult(page, size, clients.size)
	}


	private fun List<ClientModel>.asResult(page: Int, size: Int, total: Int): ClientGetPageQueryResult {
		return ClientGetPageQueryResult(PageBase(
			page = page,
			size = size,
			total = total.toLong(),
			list = this
		))
	}

}