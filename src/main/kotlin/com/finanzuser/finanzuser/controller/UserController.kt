package com.finanzuser.finanzuser.controller

import com.finanzuser.finanzuser.dto.CreateUserDTO
import com.finanzuser.finanzuser.dto.LoginDTO
import com.finanzuser.finanzuser.service.UserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
@Validated
class UserController(private val userService: UserService) {

    @PostMapping("/login")
    fun login (@Valid @RequestBody loginDTO: LoginDTO) =
        userService.login(loginDTO)


    @PostMapping("/create")
    fun create (@Valid @RequestBody createUserDTO: CreateUserDTO) =
        userService.create(createUserDTO)
}