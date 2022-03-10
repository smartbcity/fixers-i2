package i2.f2.organization.app.model

import i2.commons.model.AddressBase

fun AddressBase?.orEmpty() = this ?: AddressBase(
    street = "",
    postalCode = "",
    city = ""
)
