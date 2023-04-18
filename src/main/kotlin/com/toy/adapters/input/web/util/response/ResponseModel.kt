package com.toy.adapters.input.web.util.response

import java.io.Serializable


data class ResponseModel<T>(

    var code: String? = null,
    var message: String? = null,

    var data: T? = null

): Serializable {

}