package com.toy.framework.cache

import jakarta.annotation.Resource
import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration
// 구현예정
class RedisDistributedLockManager (

        ) {
/*

    @Resource(name = "redisObjectTemplate")
    private val redisTemplate: RedisTemplate<String, String>? = null

    // lockKey에 해당하는 value가 없으면 uniqueId로 세팅(expireTime 이후 자동 제거) (Lock Acquire 성공)
    // lockKey에 해당하는 value가 이미 존재하면 아무것도 하지 않음 (Lock Acquire 실패)
    fun tryAcquire(lockKey: String, expireTime: Duration): String {
        val uniqueId = ":"

        if (redisTemplate.se .set(lockKey, uniqueId).nx().ex(expireTime) != null) {
            return uniqueId;
        } else {
            return "LOCK_ACQUIRE_FAILED";
        }


    }
*/


}