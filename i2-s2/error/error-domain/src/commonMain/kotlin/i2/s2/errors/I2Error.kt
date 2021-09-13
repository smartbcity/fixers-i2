package i2.s2.errors

import f2.dsl.cqrs.ErrorSeverity
import f2.dsl.cqrs.ErrorSeverityError
import f2.dsl.cqrs.base.ErrorBase
import kotlinx.datetime.Clock

open class I2Error(
	type: String,
	description: String,
	payload: Map<String, String>,
	date: String = Clock.System.now().toString(),
	severity: ErrorSeverity = ErrorSeverityError()
): ErrorBase<Map<String, String>>(
	type, description, date, payload, severity)
