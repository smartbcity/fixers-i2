package i2.s2.client.f2

import f2.function.spring.adapter.f2Function
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.s2.client.domain.features.command.ClientCreateCommand
import i2.s2.client.domain.features.command.ClientCreateFunction
import i2.s2.client.domain.features.command.ClientCreatedResult
import i2.s2.keycloak.utils.toEntityCreatedId
import org.keycloak.representations.idm.ClientRepresentation
import org.keycloak.representations.idm.ProtocolMapperRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientCreateFunctionImpl {

	@Bean
	fun clientCreateFunction(): ClientCreateFunction = f2Function { cmd ->
		buildClient(cmd).let { client ->
			AuthRealmClientBuilder().build(cmd.auth).createClient(cmd.realmId, client)
		}.let { id ->
			ClientCreatedResult(id)
		}
	}

	private fun buildClient(cmd: ClientCreateCommand): ClientRepresentation {
		return ClientRepresentation().apply {
			this.clientId = cmd.clientIdentifier
			this.isPublicClient = cmd.isPublicClient
			this.isDirectAccessGrantsEnabled = cmd.isDirectAccessGrantsEnabled
			this.rootUrl = cmd.rootUrl
			this.redirectUris = cmd.redirectUris.map { url -> "${url}/*" }
			this.baseUrl = cmd.baseUrl
			this.adminUrl = cmd.adminUrl
			this.webOrigins = cmd.webOrigins
			this.protocolMappers = cmd.protocolMappers.map(::fieldMapper)
		}
	}

	private fun fieldMapper(name: String): ProtocolMapperRepresentation {
		return ProtocolMapperRepresentation().apply {
			this.name = name
			this.protocol = "openid-connect"
			this.protocolMapper = "oidc-usermodel-attribute-mapper"
			this.config = mapOf(
				"userinfo.token.claim" to "true",
				"user.attribute" to name,
				"id.token.claim" to "false",
				"access.token.claim" to "true",
				"claim.name" to name,
				"jsonType.label" to "String"
			)
		}
	}


	private fun AuthRealmClient.createClient(realmId: String, client: ClientRepresentation): String {
		return this.clients(realmId).create(client).toEntityCreatedId()
	}

}