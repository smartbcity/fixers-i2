package i2.commons.model

interface Address {
    val street: String
    val postalCode: String
    val city: String
}

data class AddressBase(
    override val street: String,
    override val postalCode: String,
    override val city: String
): Address
