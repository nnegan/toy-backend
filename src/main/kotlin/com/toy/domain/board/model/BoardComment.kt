package com.toy.domain.board.model

data class BoardComment(
    val id: Long? = null,
    val boardId: String,
    val body: String,
)
