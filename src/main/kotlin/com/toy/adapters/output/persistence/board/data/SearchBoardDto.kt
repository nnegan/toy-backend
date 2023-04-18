package com.toy.adapters.output.persistence.board.data

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

data class SearchBoardDto @QueryProjection constructor(
    val boardId: String,
    val title: String,
    val body: String,
    val useYn: String,
    val createdBy: String,
    val createdTime: LocalDateTime,
    val modifiedBy: String,
    val modifiedTime: LocalDateTime,
){
}