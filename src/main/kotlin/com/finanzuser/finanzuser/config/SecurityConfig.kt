package com.finanzuser.finanzuser.config

import com.finanzuser.finanzuser.repository.SecurityContextRepository
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig(private val securityContextRepository: SecurityContextRepository) {

    @Bean
    fun filterChain(serverHttpSecurity: ServerHttpSecurity, jwtAuthenticationFilter: JwtAuthenticationFilter): SecurityWebFilterChain =
        serverHttpSecurity.authorizeExchange()
            .pathMatchers("/auth/**").permitAll()
            .anyExchange().authenticated()
            .and()
            .addFilterAfter(jwtAuthenticationFilter, SecurityWebFiltersOrder.FIRST)
            .securityContextRepository(securityContextRepository)
            .formLogin().disable()
            .logout().disable()
            .httpBasic().disable()
            .csrf().disable()
            .build()

}