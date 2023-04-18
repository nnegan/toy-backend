package com.toy.framework.paging

import io.swagger.v3.oas.annotations.media.Schema

data class PageInfo(

    @Schema(required = false, description="페이지 사이즈. 기본값 10" , example = "10")
    var pageSize: Int = 10,
    @Schema(required = false, description="페이지 번호" , example = "1")
    var pageNumber: Int = 0,
    @Schema(required = false, description="Sortring 필드 (현재 적용 안됨)" , example = "modifiedTime")
    var sortField: String = "modifiedTime"
){

}
