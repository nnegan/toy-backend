package com.toy.framework.cache.redispubsub

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Service

@Service
class RedisSubscriber: MessageListener{

    private val log = LoggerFactory.getLogger(javaClass)

    private val messageList: MutableList<Any> = mutableListOf()

    override fun onMessage(message: Message, pattern: ByteArray?) {
        val redisMessage = ObjectMapper().readValue<RedisMessage>(message.body, RedisMessage::class.java)
        messageList.add(message.toString())

        log.debug("받은 메시지 : {}", message.toString())
        log.debug("메시지 sender : {}", redisMessage.sender)
        log.debug("메시지 context : {}", redisMessage.context)
    }

}