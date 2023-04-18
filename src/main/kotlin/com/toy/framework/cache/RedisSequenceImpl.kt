package com.toy.framework.cache

import jakarta.annotation.Resource
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisSequenceImpl : RedisSequence {

    private val log = LoggerFactory.getLogger(javaClass)

    @Resource(name = "redisObjectTemplate")
    private val redisTemplate: RedisTemplate<String, Long>? = null

    /**
     * 해당 sequence(채번)값을 리턴한다.
     * (해당 key가 없으면 1을 리턴함)
     *
     * @param key     : 채번에 사용되는 key값
     * @return       : 리턴값(int) = 현재값(int) + 1. (Max : 2147483647), -1 에러
     * @throws Exception 에러 throw함
     */
    override fun getSequence(key: String): Long {
        var retValue:Long = 0
        if (!checkSequence(key)) // 키가 바로 생성되지 않도록 함
            return -1
        retValue = try {
            redisTemplate!!.opsForValue().increment(key)!!
        } catch (e: NumberFormatException) {
            -1
        }
        return retValue
    }

    override fun getSequenceLeftPad8(prifix:String, key: String): String {
        var retValue:Long = 0
        log.debug("getSequenceRpad8 prifix :{}, key:{}",  prifix, key)
/*        if (!checkSequence(key)) { // 키가 바로 생성되지 않도록 함
            log.error("getSequenceRpad8 Not Key Error")
            return "Not Key Error"
        }*/
        retValue = try {
            redisTemplate!!.opsForValue().increment(key)!!
        } catch (e: NumberFormatException) {
            log.error("getSequenceRpad8 Not Number Error")
            return "Not Number Error"
        }
        log.debug("key : {}, retValue : {}", key, retValue)
        return prifix + StringUtils.leftPad(retValue.toString(), 8, "0")
    }



    /**
     * 해당 sequence 현재값을 리턴한다.
     *
     * @param key     : 채번에 사용되는 key값
     * @return       : 리턴값(int) = 현재값(int) Max : 2147483647)
     */
    override fun getCurrentValue(key: String?): Long {
        var retValue: Long = 0
        retValue = try {
            redisTemplate!!.opsForValue()[key!!]!!
        } catch (e: NumberFormatException) {
            -1
        }
        return retValue
    }

    /**
     * 해당 sequence(채번)값을 리턴 and 값이 입력 Max값보타 크면 1을 리턴한다. (cycling)
     * (해당 key가 없으면 1을 리턴함)
     *
     * @param key      : 채번에 사용되는 key값
     * @param maxValue: 해당 key가 가질 수 있는 최대값. 넘으면 1을 리턴한다.
     * @return        : 리턴값(int) = 현재값(int) + 1. (Max : 2147483647)
     * @throws Exception 에러 throw함
     */
    override fun getSequenceMax(key: String, maxValue: Long): Long {
        var rslt: Long = 0
        rslt = try {
            redisTemplate!!.opsForValue()[key]!!
        } catch (e: NumberFormatException) {
            return -1
        }
        if (rslt > maxValue) {
            redisTemplate.opsForValue()[key] = 1L
            rslt = 1
        } //if
        return rslt
    }

    override fun addSequence(key: String): Long {
        redisTemplate!!.opsForValue().set(key, 1L);
        return 1
    }

    override fun forceUpdateSequence(key: String, value: Long): Long {
        if (!checkSequence(key)) // 키가 바로 생성되지 않도록 함
            return -1
        redisTemplate!!.opsForValue()[key] = value
        return value
    }

    private fun checkSequence(key: String): Boolean {
        var check = true
        if (redisTemplate!!.opsForValue()[key] == null) check = false
        return check
    }
}