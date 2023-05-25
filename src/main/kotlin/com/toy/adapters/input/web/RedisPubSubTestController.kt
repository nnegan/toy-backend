package com.toy.adapters.input.web

import com.toy.framework.cache.redispubsub.RedisMessage
import com.toy.framework.cache.redispubsub.RedisPublisher
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RedisPubSubTestController(

    val redisPublisher: RedisPublisher
) {

    @PostMapping("api/test")
    fun pubsub(@RequestBody redisMessage: RedisMessage){
        redisPublisher.sendMessage(redisMessage);

    }

    @PostMapping("api/test2/{topicId}")
    fun pubsub2(@RequestBody redisMessage: RedisMessage, @PathVariable topicId: String){
        val channelTopic =  ChannelTopic(topicId)
        redisPublisher.publish(channelTopic,redisMessage )

    }

}