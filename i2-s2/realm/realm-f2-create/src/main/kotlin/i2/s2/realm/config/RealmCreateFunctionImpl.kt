package i2.s2.realm.config

import f2.function.spring.adapter.f2Function
import i2.keycloak.master.client.config.MasterRealmClientBuilder
import i2.s2.realm.domain.features.command.RealmCreateFunction
import i2.s2.realm.domain.features.command.RealmCreatedResult
import org.keycloak.representations.idm.RealmRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RealmCreateFunctionImpl {

	@Bean
	fun realmCreateFunction(): RealmCreateFunction = f2Function { cmd ->

		val realms = RealmRepresentation()
		realms.realm = cmd.id
		realms.accessTokenLifespan = 28800
		realms.ssoSessionIdleTimeout = 604800
		realms.ssoSessionMaxLifespan = 259200
		realms.actionTokenGeneratedByUserLifespan = 900
		realms.isVerifyEmail = true
		realms.isResetPasswordAllowed = true
		realms.passwordPolicy = "length(8)"
		realms.smtpServer = cmd.smtpServer
		realms.loginTheme = cmd.theme
		realms.accountTheme = "keycloak"
		realms.adminTheme = "keycloak"
		realms.emailTheme = cmd.theme
		realms.isInternationalizationEnabled = true

		realms.supportedLocales = setOf(cmd.locale)
		realms.defaultLocale = cmd.locale

		realms.isEnabled = true

		val masterRealm = MasterRealmClientBuilder().build(cmd.masterRealmAuth)
		masterRealm.keycloak.realms().create(realms)

		RealmCreatedResult(cmd.id)
	}

}