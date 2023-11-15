package com.finanzuser.finanzuser.service

import com.finanzuser.finanzuser.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class JwtService(val jwtProperties: JwtProperties) {

    fun getKey(secret: String): Key =
        Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secret))

    fun generateToken(userDetails: UserDetails): String =
        Jwts.builder()
            .setSubject(userDetails.username)
            .claim("roles", userDetails.authorities)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtProperties.expiration * 1000))
            .signWith(getKey(jwtProperties.secret))
            .compact()

    fun getClaims(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(getKey(jwtProperties.secret))
            .build()
            .parseClaimsJws(token)
            .body


    fun getSubject(token: String): String =
        Jwts.parserBuilder()
            .setSigningKey(getKey(jwtProperties.secret))
            .build()
            .parseClaimsJws(token)
            .body
            .subject

    fun validate(token: String): Boolean {
        try {
            getClaims(token)
            return true
        } catch (e: Exception) {
            println(e.message)
        }
        return false
    }


}