package com.toy.framework.cache

import jakarta.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RedisManager<T> {

    private val log = LoggerFactory.getLogger(javaClass)

    @Resource(name = "redisObjectTemplate")
    private val valueOps: ValueOperations<String, T>? = null

    @Resource(name = "redisObjectTemplate")
    private val redisTemplate: RedisTemplate<String, T>? = null

    /**
     * Redis에 저장된 값을 리턴한다.
     *
     * @param key redis-key
     * @return value redis-value (에러시 null을 리턴함)
     */
    fun getValue(key: String): T? {
        return try {
            log.debug("redisManager getValue --- key:$key")
            valueOps!![key]
        } catch (e: Exception) {
            log.error("redisManager getValue error : ", e)
            null
        }
    }

    /**
     * Redis에 값을 저장한다.
     *
     * @param key redis-key
     * @param val redis-value
     * @param timeout redis-해당 값이 유지(ttl)할 시간
     * @param timeUnit redis-해당 값이 유지(ttl)할 시간의 단위
     */
    fun put(key: String, `val`: T, timeout: Long, timeUnit: TimeUnit?) {
        try {
            valueOps!![key, `val`, timeout] = timeUnit!!
            log.debug("redisManager put --- key:$key")
        } catch (e: Exception) {
            log.error("redisManager put error : ", e)
        }
    }

    /**
     * Redis에 값을 저장한다.
     *
     * @param key redis-key
     * @param val redis-value
     */
    fun put(key: String, `val`: T) {
        try {
            valueOps!![key] = `val`
            log.debug("redisManager put --- key:$key")
        } catch (e: Exception) {
            log.error("redisManager put error : ", e)
        }
    }

    /**
     * delete
     */
    fun delete(key: String) {
        try {
            redisTemplate!!.delete(key)
            log.debug("redisManager delete --- key:$key")
        } catch (e: Exception) {
            log.error("redisManager delete error : ", e)
        }
    }

}
