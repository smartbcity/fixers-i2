package i2.s2.organization.f2.model

import i2.s2.organization.domain.model.AddressBase

fun AddressBase?.orEmpty() = this ?: AddressBase(
    street = "",
    postalCode = "",
    city = ""
)
