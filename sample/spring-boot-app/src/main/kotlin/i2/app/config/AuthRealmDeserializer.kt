package i2.app.config

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.module.SimpleModule
import i2.commons.utils.parseJsonTo
import i2.commons.utils.toJson
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.AuthRealmClientSecret
import i2.keycloak.master.domain.AuthRealmPassword
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AuthRealmDeserializer: JsonDeserializer<AuthRealm>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): AuthRealm {
        val json = p.codec.readTree<JsonNode>(p).toJson()

        return json.tryParseJsonTo<AuthRealmPassword>()
            ?: json.tryParseJsonTo<AuthRealmClientSecret>()
            ?: throw JsonMappingException.from(p, "Unknown type of AuthRealm")
    }

    private inline fun <reified T> String.tryParseJsonTo() = try {
        parseJsonTo(T::class.java)
    } catch (e: JsonProcessingException) {
        null
    } catch (e: JsonMappingException) {
        null
    }

    @Bean
    fun authRealmJacksonDeserializer(): Module = SimpleModule().addDeserializer(AuthRealm::class.java, this)
}
