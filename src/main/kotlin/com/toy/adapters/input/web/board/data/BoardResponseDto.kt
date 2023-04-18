package com.toy.adapters.input.web.board.data

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "API 인벤토리 응답 DTO")
data class BoardResponseDto (

    @Schema(description = "게시판 아이디")
    val boardId: String,

    @Schema(description = "게시판 제목")
    val title: String,

    @Schema(description = "게시판 내용")
    val body: String,

    @Schema(description = "사용여부")
    val useYn: String,

    // 등록, 수정 정보
    @Schema(description = "등록자 아이디")
    val createdBy: String,
    @Schema(description = "등록시간")
    val createdTime: LocalDateTime,
    @Schema(description = "수정자 아이디")
    val modifiedBy: String,
    @Schema(description = "수정시간")
    val modifiedTime: LocalDateTime,
){

}