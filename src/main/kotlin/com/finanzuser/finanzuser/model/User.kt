package com.finanzuser.finanzuser.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.finanzuser.finanzuser.shared.enum.RolePerson
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Table("user_account")
data class User(
    @Id
    var id: Long = 0,

    @Column
    var email: String,
    @Column
    var phone: String,
    @Column
    var role: RolePerson,

    @Column
    @get:JvmName("getUserUsername")
    var username: String,

    @Column
    @JsonIgnore
    @get:JvmName("getUserPassword")
    var password: String,

    ) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority(role.role))


    override fun getPassword(): String = password


    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    constructor(email: String, phone: String, username: String, password: String) : this(
        0,
        email,
        phone,
        RolePerson.CURRENT_PERSON,
        username,
        password
    )
    constructor(email: String,phone: String, username: String, password: String,role: RolePerson) : this(
        0,
        email,
        phone,
        role,
        username,
        password
    )
}
