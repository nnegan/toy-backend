package com.toy.application.port.input.user

import com.toy.domain.user.model.User

interface TokenUseCase {

    fun createToken(user: User): String

    fun parseToken(token: String): User?
}