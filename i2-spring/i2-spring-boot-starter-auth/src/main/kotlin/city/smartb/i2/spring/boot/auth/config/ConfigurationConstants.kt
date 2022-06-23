package city.smartb.i2.spring.boot.auth.config

// properties
const val I2_PREFIX = "i2"
const val JWT_ISSUER_URIS = "$I2_PREFIX.issuers[0].uri"

// conditional expressions
const val OPENID_REQUIRED_EXPRESSION = "!'\${$JWT_ISSUER_URIS:}'.isEmpty()"

const val AUTHENTICATION_REQUIRED_EXPRESSION = "($OPENID_REQUIRED_EXPRESSION)"
const val NO_AUTHENTICATION_REQUIRED_EXPRESSION = "!($AUTHENTICATION_REQUIRED_EXPRESSION)"
