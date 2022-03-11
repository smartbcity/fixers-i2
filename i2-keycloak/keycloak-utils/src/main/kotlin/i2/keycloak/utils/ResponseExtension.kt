package i2.keycloak.utils

import i2.commons.error.I2ApiError
import i2.commons.error.asI2Exception
import org.apache.http.HttpStatus
import javax.ws.rs.core.Response

fun Response.toEntityCreatedId(): String {
	return this.location.toString().substringAfterLast("/")
}

fun Response.isFailure(): Boolean {
	return this.status < HttpStatus.SC_OK || this.status >= HttpStatus.SC_BAD_REQUEST
}

fun Response.onCreationFailure(entityName: String = "entity") {
	val msg = "Error creating $entityName (code: ${status}) }"
	throw I2ApiError(
		description = msg,
		payload = emptyMap()
	).asI2Exception()
}