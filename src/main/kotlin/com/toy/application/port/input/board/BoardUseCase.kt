package com.toy.application.port.input.board

import com.toy.domain.board.model.Board
import com.toy.domain.board.model.BoardComment
import com.toy.domain.board.model.SearchBoard
import org.springframework.data.domain.Page

interface BoardUseCase {

    fun findOneBoard(boardId: String) : Board

    fun searchPageBoardList(searchBoard: SearchBoard): Page<Board>//, PageInfo>

    fun createBoard(board: Board): String

    fun modifyBoard(apiId:String, board: Board): String

    fun deleteBoard(boardId: String)

    fun forceLock(boardId: String)

    fun createComment(boardComment: BoardComment)

}