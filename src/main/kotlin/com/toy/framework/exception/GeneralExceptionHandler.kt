package com.toy.framework.exception

import com.toy.framework.exception.BaseRuntimeException
import com.toy.framework.exception.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 * @ExceptionHandler는 handle 하려는 예외 클래스를 의미합니다.
 * 이 프로젝트에서 모든 Custom Exception 은 이 GeneralExceptionHandler에 의해 처리됩니다.
 *
 * Exception 인스턴스를 통해 이곳에서 액세스할 수 있도록 해당 http 상태를 Exception 생성자에게 전달합니다.
 *
 * 이 핸들러를 사용하여 처리하려는 모든 Custom Exception들은 BaseRuntimeException class를 상속 받아야 합니다.
 * 또한 HttpStatus 속성을 부여해야 합니다.
 */
@RestControllerAdvice
class GeneralExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(BaseRuntimeException::class)
    protected fun generalHandler(ex: BaseRuntimeException): ResponseEntity<ErrorResponse> {
        val status: HttpStatus = ex.status
        val error = ErrorResponse(
            status.value(),
            status.reasonPhrase,
            ex.message
        )
        logger.error("BaseRuntimeException::", ex)
        return ResponseEntity<ErrorResponse>(error, status)
    }
}