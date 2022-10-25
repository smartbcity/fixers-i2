package i2.keycloak.utils

import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import javax.ws.rs.core.Response
import org.apache.http.HttpStatus

fun Response.toEntityCreatedId(): String {
	return this.location.toString().substringAfterLast("/")
}

fun Response.isFailure(): Boolean {
	return this.status < HttpStatus.SC_OK || this.status >= HttpStatus.SC_BAD_REQUEST
}

fun Response.onCreationFailure(entityName: String = "entity") {
	val error = this.readEntity(String::class.java)
	val msg = "Error creating $entityName (code: ${status}) }. Cause: ${error}"
	throw I2ApiError(
		description = msg,
		payload = emptyMap()
	).asI2Exception()
}

fun Response.handleResponseError(entityName: String): String {
	if (isFailure()) {
		onCreationFailure(entityName)
	}
	return toEntityCreatedId()
}
