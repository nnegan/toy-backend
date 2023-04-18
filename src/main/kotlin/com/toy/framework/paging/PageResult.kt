package com.toy.framework.paging

import io.swagger.v3.oas.annotations.media.Schema

data class PageResult(

    @Schema(description = "총 페이지수")
    var totalPages: Int,
    @Schema(description = "총 검색 갯수")
    var totalElements: Long,
    @Schema(description = "페이지 크기")
    var pageSize: Int,
    @Schema(description = "페이지 번호")
    var pageNumber: Int
){
}
