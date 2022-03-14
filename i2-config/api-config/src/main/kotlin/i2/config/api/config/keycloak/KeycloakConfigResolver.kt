package i2.config.api.config.keycloak

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import s2.spring.utils.logger.Logger
import java.io.File
import java.net.MalformedURLException
import java.net.URL
import kotlin.system.exitProcess

const val FILE = "file:"

@ConfigurationProperties(prefix = "i2")
@ConstructorBinding
data class KeycloakConfigResolver (
    val json: String?,
    val config: KeycloakConfigProperties
) {
    private val logger by Logger()

    fun getConfiguration(): KeycloakConfigProperties {
        if (json == null) {
            logger.info("Loading configuration from env variables...")
            return config!!
        }

        try {
            logger.info("Loading configuration from json file [$json]...")
            return getFile(json!!).readText().parseTo(KeycloakConfigProperties::class.java)
        } catch (e: Exception) {
            logger.error("Error configuration from json file [${json}]", e)
            exitProcess(-1)
        }
    }

    @Throws(MalformedURLException::class)
    fun getFile(filename: String): File {
        val url = getUrl(filename)
        return File(url.file)
    }

    private fun getUrl(filename: String): URL {
        return if (filename.startsWith(FILE)) {
            URL(filename)
        } else {
            Thread.currentThread().contextClassLoader.getResource(filename)!!
        }
    }

    private fun <T> String.parseTo(targetClass: Class<T>): T {
        val mapper = ObjectMapper()
            .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(KotlinModule())

        return mapper.readValue(this, targetClass)
    }
}
