package com.finanzuser.finanzuser.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class CreateUserDTO(
    @field:NotBlank(message = "username must not be blank")
    var username: String,
    @field:NotBlank(message = "password must not be blank")
    var password: String,
    @field:Email(message = "Email must not be blank")
    @field:NotBlank(message = "email must not be blank")
    var email: String,
    @field:NotBlank(message = "phone must not be blank")
    var phone: String,
)
