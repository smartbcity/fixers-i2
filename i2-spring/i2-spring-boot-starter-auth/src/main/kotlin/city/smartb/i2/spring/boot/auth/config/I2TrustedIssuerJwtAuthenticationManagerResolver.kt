package city.smartb.i2.spring.boot.auth.config

import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.ReactiveAuthenticationManagerResolver
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

class I2TrustedIssuerJwtAuthenticationManagerResolver(
    val isTrustedIssuer: (String) -> Boolean,
    val reactiveJwtAuthenticationConverter: ReactiveJwtAuthenticationConverter
): ReactiveAuthenticationManagerResolver<String> {

    private val authenticationManagers: MutableMap<String, Mono<ReactiveAuthenticationManager>> = ConcurrentHashMap()

    override fun resolve(issuer: String): Mono<ReactiveAuthenticationManager> {
        if (!isTrustedIssuer(issuer)) return Mono.empty()

        return this.authenticationManagers.computeIfAbsent(issuer) {
            Mono.fromCallable<ReactiveAuthenticationManager> {
                JwtReactiveAuthenticationManager(
                    ReactiveJwtDecoders.fromIssuerLocation(issuer)
                ).apply {
                    setJwtAuthenticationConverter(reactiveJwtAuthenticationConverter)
                }
            }.subscribeOn(Schedulers.boundedElastic())
                .cache(
                    { Duration.ofMillis(Long.MAX_VALUE) },
                    { ex: Throwable? -> Duration.ZERO }
                ) { Duration.ZERO }
        }
    }
}
