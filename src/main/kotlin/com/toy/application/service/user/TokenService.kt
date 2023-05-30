package com.toy.application.service.user

import com.toy.application.port.input.user.TokenUseCase
import com.toy.application.port.output.repository.user.UserPort
import com.toy.domain.user.model.User
import org.springframework.security.oauth2.jwt.*
import org.springframework.stereotype.Service
import java.lang.Exception
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class TokenService(

    private val jwtDecoder: JwtDecoder,
    private val jwtEncoder: JwtEncoder,
    private val userPort: UserPort,

    ) : TokenUseCase {

    override fun createToken(user: User): String {
        // JwsAlgorithm 선언
        // HS256 -> 대칭키를 이용한 알고리즘, 해싱과 공유키를 이용한 MAC가 HMAC
        // RS256 -> 비대칭형 알고리즘, 공개키(Public Key)와 개인키(Private Key) 2개의 키를 활용
        val jwsHeader = JwsHeader.with { "HS256" }.build()
        val claims = JwtClaimsSet.builder()
            .issuedAt(Instant.now()) // JWT가 발행된 시간
            .expiresAt(Instant.now().plus(30L, ChronoUnit.DAYS))   // JWT가 만료 시간
            .subject(user.userNo.toString()) // 식별자
            .claim("userId", user.userId)
            .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).tokenValue
    }

    override fun parseToken(token: String): User? {
        return try {
            val jwt = jwtDecoder.decode(token)
            val userId = jwt.claims["userId"] as String

            userPort.findByUserId(userId)

        } catch (e: Exception) {
            null
        }
    }

}