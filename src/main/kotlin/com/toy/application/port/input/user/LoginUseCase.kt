package com.toy.application.port.input.user

import com.toy.domain.user.model.User

interface LoginUseCase {

    fun loginUser(user: User) : String
}