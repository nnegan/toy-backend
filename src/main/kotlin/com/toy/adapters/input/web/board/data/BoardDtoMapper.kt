package com.toy.adapters.input.web.board.data


import com.toy.domain.board.model.Board
import com.toy.domain.board.model.SearchBoard
import com.toy.framework.paging.PageInfo
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class BoardDtoMapper{

    fun domainToResponseDto(board: Board) = with(board){
        BoardResponseDto(boardId!!, title, body, useYn, createdBy!!, createdAt!!, modifiedBy!!, modifiedAt!!)
    }


    fun requestDtoToDomain(boardId: String, boardRequestDto: BoardRequestDto, registId: String) = with(boardRequestDto){
        Board(
            boardId = boardId,
            title = title,
            body = body,
            useYn = useYn,
            createdBy = registId,
            modifiedBy = registId)

    }

    fun searchPageRequestDtoToDomain(searchPageBoardRequestDto: SearchPageBoardRequestDto) = with(searchPageBoardRequestDto){
        SearchBoard(
            boardId,
            title,
            body,
            useYn,
            createdBy,
            toCreatedTime,
            endCreatedTime,
            modifiedBy,
            toModifiedTime,
            endModifiedTime,
            pageInfo
        )
    }

}