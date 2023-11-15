package com.finanzuser.finanzuser.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class UnauthorizedException(msg: String) : RuntimeException(msg)
