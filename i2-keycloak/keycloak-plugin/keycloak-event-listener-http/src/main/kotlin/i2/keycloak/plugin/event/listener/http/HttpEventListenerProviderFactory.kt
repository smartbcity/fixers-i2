package i2.keycloak.plugin.event.listener.http

import org.keycloak.Config
import org.keycloak.events.EventListenerProvider
import org.keycloak.events.EventListenerProviderFactory
import org.keycloak.models.KeycloakSession
import org.keycloak.models.KeycloakSessionFactory

class HttpEventListenerProviderFactory: EventListenerProviderFactory {
    override fun create(session: KeycloakSession): EventListenerProvider {
        return HttpEventListenerProvider(session)
    }
    override fun init(config: Config.Scope) {}
    override fun postInit(factory: KeycloakSessionFactory) {}
    override fun getId(): String = "i2-event-http"
    override fun close() {}
}