package com.toy.application.port.output.repository.user

import com.toy.domain.user.model.User

interface UserPort {

    fun findByUserId(userId: String): User
}