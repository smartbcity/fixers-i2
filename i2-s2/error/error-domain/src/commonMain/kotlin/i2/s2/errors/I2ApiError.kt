package i2.s2.errors

import f2.dsl.cqrs.ErrorSeverityError

class I2ApiError(
	override val description: String,
	override val payload: Map<String, String>,
): I2Error(
	type = I2ApiError::class.simpleName!!,
	severity = ErrorSeverityError(),
	description = description,
	payload = payload,
)
