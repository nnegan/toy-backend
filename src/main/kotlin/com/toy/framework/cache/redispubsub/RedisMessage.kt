package com.toy.framework.cache.redispubsub

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class RedisMessage (

    @JsonProperty("sender")
    val sender: String,

    @JsonProperty("context")
    val context: String,

)
