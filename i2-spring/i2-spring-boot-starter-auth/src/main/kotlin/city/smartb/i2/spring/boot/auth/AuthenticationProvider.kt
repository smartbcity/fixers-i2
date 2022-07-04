package city.smartb.i2.spring.boot.auth

import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.reactor.ReactorContext
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import reactor.core.publisher.Mono

const val ORGANIZATION_ID_CLAIM_NAME = "memberOf"

object AuthenticationProvider {
    suspend fun getSecurityContext(): SecurityContext? {
        return coroutineContext[ReactorContext.Key]
            ?.context?.get<Mono<SecurityContext>>(SecurityContext::class.java)
            ?.awaitSingleOrNull()
    }

    suspend fun getAuthentication(): JwtAuthenticationToken? {
        return getSecurityContext()?.authentication as JwtAuthenticationToken?
    }

    suspend fun getPrincipal(): Jwt? {
        return getAuthentication()?.principal as Jwt?
    }

    suspend fun getOrganizationId(): String? {
        return getPrincipal()?.getClaim<String>(ORGANIZATION_ID_CLAIM_NAME)
    }

    suspend fun getIssuer(): String {
        return getPrincipal()?.issuer.toString()
    }

    suspend fun hasRole(role: String): Boolean {
        val authorities = getAuthentication()?.authorities ?: return false
        authorities.any { it.authority == role }
        return authorities.contains(SimpleGrantedAuthority(role))
    }
}
