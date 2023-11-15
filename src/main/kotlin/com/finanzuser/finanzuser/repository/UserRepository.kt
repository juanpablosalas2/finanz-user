package com.finanzuser.finanzuser.repository

import com.finanzuser.finanzuser.model.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository : ReactiveCrudRepository<User,Long>{

    fun findByUsernameOrEmail(username:String, email:String) : Mono<User>
}