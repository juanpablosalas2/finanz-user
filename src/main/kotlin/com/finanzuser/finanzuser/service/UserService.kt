package com.finanzuser.finanzuser.service

import com.finanzuser.finanzuser.dto.CreateUserDTO
import com.finanzuser.finanzuser.dto.LoginDTO
import com.finanzuser.finanzuser.dto.TokenDTO
import com.finanzuser.finanzuser.exception.NotFoundException
import com.finanzuser.finanzuser.model.User
import com.finanzuser.finanzuser.repository.UserRepository
import com.finanzuser.finanzuser.shared.enum.RolePerson
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
) {

    fun login(loginDTO: LoginDTO): Mono<TokenDTO> =
        userRepository.findByUsernameOrEmail(loginDTO.username, loginDTO.username)
            .filter { passwordEncoder.matches(loginDTO.password, it.getPassword()) }
            .map { TokenDTO(jwtService.generateToken(it)) }
            .switchIfEmpty(Mono.error(NotFoundException("bad credentials")))


    fun create(createUserDTO: CreateUserDTO): Mono<User> {
        val user = User(
            createUserDTO.email,
            createUserDTO.phone,
            createUserDTO.username,
            passwordEncoder.encode(createUserDTO.password),
            RolePerson.PAID_PERSON
        )
        val userExist = userRepository.findByUsernameOrEmail(user.username, user.email).hasElement()

        return userExist.flatMap { exist ->
            if (exist) {
                Mono.error(NotFoundException("username or email already exist"))
            } else userRepository.save(user)
        }
    }
}