package com.toy.adapters.output.persistence.user

import com.toy.application.port.output.repository.user.UserPort
import com.toy.domain.user.model.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter (
    private val userRepository: UserRepository,

) : UserPort {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun findByUserId(userId: String): User {
        val result = userRepository.findByUserId(userId)

        return User(
            userNo = result.userNo,
            userId = result.userId,
            userPassword = result.userPassword,
            userName = result.userName,
        )
    }
}