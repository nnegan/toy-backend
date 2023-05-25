package com.toy.framework.cache.redispubsub

import jakarta.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.stereotype.Service


@Service
class RedisPublisher(
    private val redisSubscriber: RedisSubscriber,
    private val redisMessageListener: RedisMessageListenerContainer
){

    private val log = LoggerFactory.getLogger(javaClass)

    @Resource(name = "redisObjectTemplate")
    private val redisTemplate: RedisTemplate<String, Object>? = null

    fun sendMessage(redisMessage: RedisMessage){
        log.debug("redisMessage : {}", redisMessage)
       // sendMessage(ChannelTopic("topic-1"), redisMessage)
        redisTemplate!!.convertAndSend("topic1", redisMessage)
    }

    /*fun sendMessage(topic: ChannelTopic, message: RedisMessage) {
        redisTemplate!!.convertAndSend(topic.topic, message)
    }*/

    // 발행시 토픽 생성
    fun publish(topic: ChannelTopic, message: RedisMessage) {

        redisMessageListener.addMessageListener(redisSubscriber, topic);
//        channels.put(roomId, channel)
        log.debug("redisMessage : {}, {}", topic, message)
        redisTemplate!!.convertAndSend(topic.topic, message)
    }
}