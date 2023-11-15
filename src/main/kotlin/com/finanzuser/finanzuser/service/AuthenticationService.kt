package com.finanzuser.finanzuser.service

import com.finanzuser.finanzuser.exception.UnauthorizedException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.stream.Stream

@Service
class AuthenticationService(val jwtService: JwtService) : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication?): Mono<Authentication> =
        Mono.just(authentication!!)
            .map { jwtService.getClaims(it?.credentials.toString()) }
            .log()
            .onErrorResume { Mono.error(UnauthorizedException("bad token")) }
            .map {
                UsernamePasswordAuthenticationToken(it.subject,
                    null,
                    Stream.of(it["roles"])
                        .map { role -> role as (List<Map<String, String>>) }
                        .flatMap { role ->
                            role.stream().map { authority -> authority.get("authority") }
                                .map { authority -> authority?.let { SimpleGrantedAuthority(it) } }
                        }
                        .toList())
            }
}