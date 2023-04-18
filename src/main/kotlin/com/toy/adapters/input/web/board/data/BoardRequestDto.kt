package com.toy.adapters.input.web.board.data

import io.swagger.v3.oas.annotations.media.Schema


@Schema(description = "게시판 생성/수정 DTO")
data class BoardRequestDto (

    @Schema(description = "게시판 제목")
    val title: String,

    @Schema(description = "게시판 내용")
    val body: String,

    @Schema(description = "사용여부")
    val useYn: String,

){

}

data class BoardCommentRequestDto(

    val body: String,

)