package city.smartb.i2.spring.boot.auth.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerReactiveAuthenticationManagerResolver
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authorization.AuthorizationContext
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.annotation.security.PermitAll
import javax.annotation.security.RolesAllowed

@Suppress("UnnecessaryAbstractClass")
@Configuration
@ConditionalOnMissingBean(WebSecurityConfig::class)
@EnableConfigurationProperties(I2TrustedIssuersConfig::class)
abstract class WebSecurityConfig {

    companion object {
        const val SPRING_SECURITY_FILTER_CHAIN = "springSecurityFilterChain"
    }

    @Value("\${spring.cloud.function.web.path:}")
    lateinit var contextPath: String

    @Autowired
    lateinit var applicationContext: ApplicationContext

    @Autowired
    lateinit var i2TrustedIssuersResolver: I2TrustedIssuersConfig

    @Bean
    @ConfigurationProperties(prefix = "i2.filter")
    fun authFilter(): Map<String, String> = HashMap()

    @Bean
    fun trustedIssuers(): List<String> {
        return i2TrustedIssuersResolver.getTrustedIssuers()
    }

    @Bean(SPRING_SECURITY_FILTER_CHAIN)
    @ConditionalOnExpression(NO_AUTHENTICATION_REQUIRED_EXPRESSION)
    fun dummyAuthenticationProvider(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.authorizeExchange().anyExchange().permitAll()
        http.csrf().disable()
        http.corsConfig()
        return http.build()
    }

    @Bean(SPRING_SECURITY_FILTER_CHAIN)
    @ConditionalOnExpression(AUTHENTICATION_REQUIRED_EXPRESSION)
    fun oauthAuthenticationProvider(http: ServerHttpSecurity): SecurityWebFilterChain {
        addAuthenticationRules(http)
        http.csrf().disable()
        http.corsConfig()
        return http.build()
    }

    fun addAuthenticationRules(http: ServerHttpSecurity) {
        addRolesAllowedRules(http)
        addPermitAllRules(http)
        addMandatoryAuthRules(http)
        addJwtParsingRules(http)
    }

    private fun authenticate(authentication: Mono<Authentication>, context: AuthorizationContext): Mono<AuthorizationDecision> {
        return authentication.map { auth ->
            if (auth !is JwtAuthenticationToken || auth.token == null) {
                return@map false
            }

            val filters = authFilter()
            filters.isEmpty() || filters.all { (key, value) -> auth.token.claims[key] == value }
        }.map(::AuthorizationDecision)
    }

    private fun ServerHttpSecurity.corsConfig() {
        val config = CorsConfiguration()
        config.allowedOrigins = listOf("*")
        config.allowCredentials = false
        config.addAllowedMethod("*")
        config.addAllowedHeader("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        cors().configurationSource(source)
    }

    fun addRolesAllowedRules(http: ServerHttpSecurity) {
        applicationContext.getBeanNamesForAnnotation(RolesAllowed::class.java)
            .associateWith { bean ->
                applicationContext.findAnnotationOnBean(bean, RolesAllowed::class.java)!!.value
            }
            .forEach { (name, roles) ->
                http.authorizeExchange()
                    .pathMatchers("$contextPath/$name")
                    .hasAnyRole(*roles)
            }
    }

    fun addPermitAllRules(http: ServerHttpSecurity) {
        val permitAllBeans = applicationContext.getBeanNamesForAnnotation(PermitAll::class.java)
            .map { bean -> "$contextPath/$bean" }
            .toTypedArray()

        if (permitAllBeans.isNotEmpty()) {
            http.authorizeExchange()
                .pathMatchers(*permitAllBeans)
                .permitAll()
        }
    }

    fun addMandatoryAuthRules(http: ServerHttpSecurity) {
        http.authorizeExchange()
            .anyExchange()
            .access(::authenticate)
    }

    fun addJwtParsingRules(http: ServerHttpSecurity) {
        val i2TrustedIssuerJwtAuthenticationManagerResolver = I2TrustedIssuerJwtAuthenticationManagerResolver(
            ::isTrustedIssuer,
            jwtAuthenticationConverter()
        )

        http.oauth2ResourceServer { oauth2 ->
            oauth2.authenticationManagerResolver(
                JwtIssuerReactiveAuthenticationManagerResolver(i2TrustedIssuerJwtAuthenticationManagerResolver)
            )
        }
    }

    fun jwtAuthenticationConverter(): ReactiveJwtAuthenticationConverter {
        return ReactiveJwtAuthenticationConverter().apply {
            setJwtGrantedAuthoritiesConverter(::jwtAuthoritiesConverter)
        }
    }

    fun jwtAuthoritiesConverter(jwt: Jwt): Flux<GrantedAuthority> {
        val realmAccess = jwt.claims["realm_access"] as Map<String, List<String>>?
        return realmAccess?.get("roles").orEmpty().map { role ->
            SimpleGrantedAuthority("ROLE_$role")
        }.let { Flux.fromIterable(it) }
    }

    fun isTrustedIssuer(issuer: String): Boolean {
        return issuer in trustedIssuers()
    }
}
