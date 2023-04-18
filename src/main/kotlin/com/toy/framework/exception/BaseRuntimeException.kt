package com.toy.framework.exception

import org.springframework.http.HttpStatus

/**
 * 응답으로 반환하려는 특정 HttpStatus를 참조합니다.
 *
 * GeneralExceptionHandler로 Http 상태를 직접 보냅니다.
 * 따라서 Exception 또는 상태 코드 별로 다른 ExceptionHandler가 필요하지 않습니다.
 * @see GeneralExceptionHandler
 */
class BaseRuntimeException(
    message: String,
    val status: HttpStatus
) : RuntimeException(message) {
    //public val status1: HttpStatus = status
}