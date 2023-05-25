package com.toy.adapters.input.web.user

import com.toy.adapters.input.web.user.data.LoginRequestDto
import com.toy.application.port.input.user.LoginUseCase
import com.toy.domain.user.model.User
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController(
    private val loginUseCase: LoginUseCase,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("/check")
    fun loginUser(@RequestBody loginRequestDto: LoginRequestDto) : String {
        //val token: String = "";
        log.debug("loginUser {}", loginRequestDto.toString())


        return loginUseCase.loginUser(User(userId = loginRequestDto.loginId, userPassword = loginRequestDto.loginPassword))

        //return token

    }


}