package com.finanzuser.finanzuser.dto

import javax.validation.constraints.NotBlank

data class LoginDTO(
    @field:NotBlank(message = "username must not be blank")
    var username: String,
    @field:NotBlank(message = "password must not be blank")
    var password: String)
