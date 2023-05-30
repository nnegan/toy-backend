package com.toy.application.service.user

import com.toy.application.port.input.user.LoginUseCase
import com.toy.application.port.output.repository.user.UserPort
import com.toy.domain.user.model.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LoginService (
    private val userPort: UserPort,
    private val tokenService: TokenService,

) : LoginUseCase {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun loginUser(user: User): String {

        val result = userPort.findByUserId(user.userId!!)

        log.debug("result : {}", result.toString())

        return tokenService.createToken(user)
    }
}