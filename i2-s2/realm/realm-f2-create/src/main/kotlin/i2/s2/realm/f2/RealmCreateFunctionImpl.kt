package i2.s2.realm.f2

import f2.function.spring.adapter.f2Function
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.keycloak.realm.client.config.buildRealmRepresentation
import i2.keycloak.realm.client.config.realmsResource
import i2.s2.realm.domain.features.command.RealmCreateCommand
import i2.s2.realm.domain.features.command.RealmCreateFunction
import i2.s2.realm.domain.features.command.RealmCreatedResult
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RealmCreateFunctionImpl {

	@Bean
	fun realmCreateFunction(): RealmCreateFunction = f2Function { cmd ->
		val masterRealm = AuthRealmClientBuilder().build(cmd.masterRealmAuth)
		masterRealm.createRealm(cmd)
		RealmCreatedResult(cmd.id)
	}

	private fun AuthRealmClient.createRealm(cmd: RealmCreateCommand) {
		val realms = buildRealmRepresentation(
			realm = cmd.id,
			smtpServer = cmd.smtpServer,
			theme = cmd.theme,
			locale = cmd.locale
		)
		realmsResource().create(realms)
	}
}
