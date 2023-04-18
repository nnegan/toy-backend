package com.toy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ToyBackendApplication

fun main(args: Array<String>) {
    runApplication<ToyBackendApplication>(*args)
}
