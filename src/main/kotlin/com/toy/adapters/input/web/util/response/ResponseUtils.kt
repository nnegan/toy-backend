package com.toy.adapters.input.web.util.response

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

class ResponseUtils {


    companion object {

        private val log = LoggerFactory.getLogger(javaClass)

        fun <T> added(body: T): ResponseEntity<T> {
            return getOkResponseEntityWithBody("", body)
        }
        fun <T> added(message:String, body: T): ResponseEntity<T> {
            return getOkResponseEntityWithBody(message, body)
        }

        fun <T> added(body: T, path: String, vararg uriVariables: Any): ResponseEntity<T> {
            //Create resource location
            val location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(path)
                .buildAndExpand(*uriVariables)
                .toUri()
            return added(body, location)
        }

        fun <T> added(body: T, location: URI): ResponseEntity<T> {
            return getCreatedResponseEntityWithBody(body, location)
        }

        fun <T> modified(body: T): ResponseEntity<T> {
            return getOkResponseEntityWithBody("", body)
        }
        fun <T> modified(message: String, body: T): ResponseEntity<T> {
            return getOkResponseEntityWithBody(message, body)
        }

        fun deleted(): ResponseEntity<Any> {
            return ResponseEntity.noContent().build()
        }

        fun <T> deleted(body: T): ResponseEntity<T> {
            return getOkResponseEntityWithBody("", body)
        }

        fun <T> compared(body: T): ResponseEntity<T> {
            return getOkResponseEntityWithBody("", body)
        }

        fun <T> searchResults(body: T): ResponseEntity<T> {
            return getOkResponseEntityWithBody("", body)
        }

        private fun <T> getOkResponseEntityWithBody(message: String, body: T): ResponseEntity<T> {
            return ResponseEntity.ok().body(ResponseModel("SUCCESS", message, body)) as ResponseEntity<T>
        }

        private fun <T> getCreatedResponseEntityWithBody(body: T, location: URI): ResponseEntity<T> {
            return ResponseEntity.created(location).body(ResponseModel("", "", body)) as ResponseEntity<T>
        }

        fun <T> failBadRequest(): ResponseEntity<T> {
            return ResponseEntity.badRequest().body(ResponseModel("FAIL", "", "")) as ResponseEntity<T>
        }

        fun <T> failBadRequest(message: String?): ResponseEntity<T> {
            return ResponseEntity.badRequest().body(ResponseModel("FAIL", message, "")) as ResponseEntity<T>
        }

        fun <T> failServerError(): ResponseEntity<T> {
            return ResponseEntity.internalServerError().body(ResponseModel("FAIL", "", "")) as ResponseEntity<T>
        }

        fun <T> failServerError(message: String?): ResponseEntity<T> {
            return ResponseEntity.internalServerError().body(ResponseModel("FAIL", message, "")) as ResponseEntity<T>
        }
    }
}