package com.toy.domain.board.model

import com.toy.framework.paging.PageInfo

data class SearchBoard (

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

    var pageInfo: PageInfo? = null,

    ){
}