package i2.s2.user.f2

import f2.dsl.cqrs.base.PageBase
import f2.function.spring.adapter.f2Function
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.keycloak.realm.domain.UserModel
import i2.keycloak.realm.domain.features.query.UserGetPageQueryFunction
import i2.keycloak.realm.domain.features.query.UserGetPageQueryResult
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserGetPageQueryFunctionImpl {

	@Bean
	fun userGetPageQueryFunctionImpl(): UserGetPageQueryFunction = f2Function { cmd ->
		val realmClient = AuthRealmClientBuilder().build(cmd.auth)
		val size = cmd.page.size ?: 10
		val page = cmd.page.page ?: 1
		val first = page * size
		val max = first + size
		val count = realmClient.keycloak.realm(cmd.realmId).users().count()
		realmClient.keycloak.realm(cmd.realmId).users().list(first, max)
			.asModels()
			.asResult(page, size, count)
	}


	private fun List<UserModel>.asResult(page: Int, size: Int, total: Int): UserGetPageQueryResult {
		return UserGetPageQueryResult(PageBase(
			page = page,
			size = size,
			total = total.toLong(),
			list = this
		))
	}

}