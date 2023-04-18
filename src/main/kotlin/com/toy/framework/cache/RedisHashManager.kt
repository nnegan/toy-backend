package com.toy.framework.cache

import jakarta.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisHashManager<T>{

    private val log = LoggerFactory.getLogger(javaClass)

    @Resource(name = "redisObjectTemplate")
    private val hashOps: HashOperations<String, String, T>? = null

    @Resource(name = "redisObjectTemplate")
    private val redisTemplate: RedisTemplate<String, T>? = null

    // Value 건

    // 단건

    /**
     * Hash 조회 : 방식 -> 단건으로 조회 Key + HashKey
     */
    fun getHashValue(key: String, hashKey: String): T? {
        return try {
            log.debug("redisHashManager getHashValue --- key:$key hashKey:$hashKey")
            hashOps!!.get(key, hashKey)
        } catch (e: Exception) {
            log.error("redisHashManager getHashValue error : ", e)
            null
        }
    }

    /**
     * Hash 저장 : 방식 -> 단건으로 저장 Key + HashKey
     */
    fun putHashValue(key: String, hashKey: String, `val`: T) {
        try {
            hashOps!!.put(key, hashKey, `val`)

            log.debug("redisHashManager putHashValue --- key:$key")
        } catch (e: Exception) {
            log.error("redisHashManager putHashValue error : ", e)
        }
    }

    /**
     * Hash 삭제 : 방식 -> 단건로 삭제 Key + HashKey
     */
    fun deleteHashValue(key: String, hashKey: String) {
        try {
            hashOps!!.delete(key, hashKey)

            log.debug("redisHashManager deleteHashValue --- key:$key hashKey:$hashKey")
        } catch (e: Exception) {
            log.error("redisHashManager deleteHashValue error : ", e)
        }
    }


    /**
     * Hash 저장 : 방식 -> 단건으로 저장 Key + HashKey
     */
    fun putAllHashValue(key: String, hashKey: Map<String, T>) {

        try {
            hashOps!!.putAll(key, hashKey)

            log.debug("redisHashManager putAllHashValue --- key:$key")
        } catch (e: Exception) {
            log.error("redisHashManager putAllHashValue error : ", e)
        }
    }

    /**
     * Hash 삭제 : 방식 -> 단건로 삭제 Key + HashKey
     */
    fun deleteHashAll(key: String) {
        try {
            redisTemplate!!.delete(key)

            log.debug("redisHashManager deleteHashAll --- key:$key")
        } catch (e: Exception) {
            log.error("redisHashManager deleteHashAll error : ", e)
        }
    }

}
