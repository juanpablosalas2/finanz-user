package com.finanzuser.finanzuser.config

import com.finanzuser.finanzuser.exception.NotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Service
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Service
class JwtAuthenticationFilter : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val request: ServerHttpRequest = exchange.request
        val path = request.path.value()
        if (path.contains("auth"))
            return chain.filter(exchange)

        val auth: String = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
            ?: return Mono.error {
                NotFoundException("no token was found")
            }

        if (!auth.startsWith("Bearer ")) {
            return Mono.error { NotFoundException("no token was found") }
        }

        val token = auth.replace("Bearer ", "")
        exchange.attributes.put("token", token)
        return chain.filter(exchange)

    }
}