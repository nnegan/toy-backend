package com.toy.application.port.output.repository.board

import com.toy.domain.board.model.Board
import com.toy.domain.board.model.BoardComment
import com.toy.domain.board.model.SearchBoard
import org.springframework.data.domain.Page

interface BoardPort {

    fun findOneBoard(boardId: String) : Board

    fun searchPageBoardList(searchBoard: SearchBoard): Page<Board>

    fun createBoard(board: Board): String

    fun modifyBoard(boardId:String, board: Board): String

    fun deleteBoard(boardId: String)

    fun createComment(boardComment: BoardComment)

}