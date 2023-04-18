package com.toy.adapters.input.web.board.data

import com.toy.framework.paging.PageInfo
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "게시판 조회 DTO : 조회 기본값 설정")
data class SearchPageBoardRequestDto(

    @Schema(description = "")
    val boardId: String? = null,

    val title: String? = null,
    val body: String? = null,
    val useYn: String? = null,

    // 등록, 수정 검색 정보
    var createdBy: String? = null,
    var toCreatedTime: String? = null,
    var endCreatedTime: String? = null,

    var modifiedBy: String? = null,
    var toModifiedTime: String? = null,
    var endModifiedTime: String? = null,

    var pageInfo: PageInfo? = PageInfo(10, 0, "modifiedTime"),

){

}
