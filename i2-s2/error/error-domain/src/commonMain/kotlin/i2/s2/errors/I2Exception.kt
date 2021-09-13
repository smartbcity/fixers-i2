package i2.s2.errors

class I2Exception(
	val error: I2Error,
	val from: Throwable? = null
): Exception(error.description, from)

fun I2Error.asI2Exception(from: Throwable? = null): I2Exception = I2Exception(this, from)
