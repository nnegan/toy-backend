package com.toy.domain.board.model

import java.time.LocalDateTime

data class Board(

    val boardId: String? = null,

    val title: String,
    val body: String,

    val comments: List<BoardComment>? = emptyList(),

    val useYn: String,

    // 등록, 수정 정보
    val createdBy: String? = null,
    val createdTime: LocalDateTime? = null,
    val modifiedBy: String? = null,
    val modifiedTime: LocalDateTime? = null,
)
