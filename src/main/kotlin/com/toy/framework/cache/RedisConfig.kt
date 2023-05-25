package com.toy.framework.cache

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisClusterConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration


@Configuration
class RedisConfig
{

    @Value("\${spring.config.redis.object.host}")
    private lateinit var host: String

    @Value("\${spring.config.redis.object.port}")
    private var port: Int = 0

    @Value("\${spring.config.redis.object.cluster.nodes}")
    private lateinit var clusterNodes: List<String>

    @Value("\${spring.config.redis.object.use-cluster}")
    private lateinit var useCluster: String

    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun redisObjectTemplateFactory(): RedisConnectionFactory {
        //log.debug("host : {}, port : {}, useCluster : {}", host, port, useCluster)
        val lettuceClientConfiguration: LettuceClientConfiguration = LettuceClientConfiguration.builder()
            .commandTimeout(Duration.ofMinutes(1))
            .shutdownTimeout(Duration.ZERO) // .readFrom(ReadFrom.REPLICA_PREFERRED)                     // 읽기 명령은 replica에서 우선으로 실행
            // .clientOptions(getClusterClientOptions(redisProperties))
            // .clientResources(clientResources)
            .build()
        return if ("Y" == useCluster) {
            val redisClusterConfiguration = RedisClusterConfiguration(clusterNodes)
            LettuceConnectionFactory(redisClusterConfiguration, lettuceClientConfiguration)
        } else {
            LettuceConnectionFactory(RedisStandaloneConfiguration(host, port), lettuceClientConfiguration)
        }
    }

    @Bean(name = ["redisObjectTemplate"])
    fun redisObjectTemplate(): RedisTemplate<String,Any> {
        val redisTemplate: RedisTemplate<String, Any> = RedisTemplate<String, Any>()
        redisTemplate.setDefaultSerializer(RedisSerializer.json())
        redisTemplate.keySerializer = RedisSerializer.string()
        redisTemplate.hashKeySerializer = RedisSerializer.string()
        redisTemplate.setConnectionFactory(redisObjectTemplateFactory())
        return redisTemplate
    }



    @Bean
    fun RedisMessageListener(connectionFactory: RedisConnectionFactory): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(connectionFactory)
        return container
    }

    @Bean
    fun redisTemplateForObject(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.setConnectionFactory(connectionFactory)
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = Jackson2JsonRedisSerializer(String::class.java)
        return redisTemplate
    }


/*
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory = LettuceConnectionFactory(host, port)

    @Bean
    fun cacheManager(): CacheManager {
        val objectMapper = ObjectMapper()
            .registerModule(JavaTimeModule())
            .activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Any::class.java).build(), ObjectMapper.DefaultTyping.EVERYTHING)

        val redisCacheConfiguration: RedisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair
                .fromSerializer(GenericJackson2JsonRedisSerializer(objectMapper)))
            .entryTtl(Duration.ofMinutes(3L))

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory())
            .cacheDefaults(redisCacheConfiguration).build()
    }*/

/*
    @Bean(name = ["redisObjectTemplate"])
    @Qualifier("redisObjectTemplate")
    fun redisObjectTemplate(): RedisTemplate<String,Any> {
        val redisTemplate: RedisTemplate<String, Any> = RedisTemplate<String, Any>()
        val stringSerializer: RedisSerializer<String> = StringRedisSerializer()
        redisTemplate.setKeySerializer(stringSerializer)
        redisTemplate.setValueSerializer(stringSerializer)
        redisTemplate.setHashKeySerializer(stringSerializer)
        redisTemplate.setHashValueSerializer(stringSerializer)
        redisTemplate.setDefaultSerializer(stringSerializer)
        redisTemplate.setConnectionFactory(redisObjectTemplateFactory())
        return redisTemplate
    }
    */


    /*

    @Bean(name = ["redisObjectTemplate"])
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory?): RedisTemplate<Any, Any>? {
        val template = RedisTemplate<Any, Any>()
        template.setDefaultSerializer(RedisSerializer.json())
        template.keySerializer = RedisSerializer.string()
        template.hashKeySerializer = RedisSerializer.string()
        template.setConnectionFactory(redisConnectionFactory!!)
        return template
    }*/


    /* private val PATTERN = "__keyevent@*__:expired"
    @Bean
    fun redisMessageListenerContainer(
        redisConnectionFactory: RedisConnectionFactory?,
        expirationListener: ExpirationListener?
    ): RedisMessageListenerContainer {
        val redisMessageListenerContainer = RedisMessageListenerContainer()
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory)
        redisMessageListenerContainer.addMessageListener(expirationListener, PatternTopic(PATTERN))
        redisMessageListenerContainer.setErrorHandler { e ->
            *//*log.error(
                "There was an error in redis key expiration listener container",
                e
            )*//*
        }
        return redisMessageListenerContainer
    }*/
}
