package city.smartb.i2.spring.boot.auth.config

open class I2TrustedIssuerProperties(
    open val name: String,
    open val authUrl: String,
    open val realm: String,
    open val uri: String = "${authUrl.removeSuffix("/")}/realms/${realm.removePrefix("/")}"
)
