package com.toy.adapters.output.persistence.board

import com.toy.application.port.output.repository.board.BoardPort
import com.toy.domain.board.model.Board
import com.toy.domain.board.model.BoardComment
import com.toy.domain.board.model.SearchBoard
import com.toy.framework.exception.ResourceNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class BoardPersistenceAdapter (
    private val boardRepository: BoardRepository,
    private val boardMapper: BoardMapper,
    private val boardCommentRepository: BoardCommentRepository,

) : BoardPort {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun findOneBoard(boardId: String): Board {
        return boardRepository.findById(boardId).map(boardMapper::mapToDomain).orElseThrow { ResourceNotFoundException("[$boardId] not found") }
    }

    override fun searchPageBoardList(searchBoard: SearchBoard): Page<Board> {
        return boardRepository.searchBoardPageList(searchBoard)
    }

    override fun createBoard(board: Board): String {
        log.debug("board {}", board.toString())
        log.debug("boardMapper.mapToEntity(board) : {}", boardMapper.mapToEntity(board).toString())
        return boardRepository.save(boardMapper.mapToEntity(board)).boardId
    }

    override fun modifyBoard(boardId: String, board: Board): String {
        val savedEntity = findByIdOrThrow(boardId)
        log.debug("board {}, savedEntity {}", board.toString(), savedEntity.toString())
        boardRepository.save(boardMapper.copyToEntity(board, savedEntity))
        return "success"
    }

    override fun deleteBoard(boardId: String) {
        TODO("Not yet implemented")
    }

    private fun findByIdOrThrow(boardId: String): BoardEntity =
        boardRepository.findById(boardId).orElseThrow { ResourceNotFoundException("[$boardId] not found") }


    override fun createComment(boardComment: BoardComment) {
        boardCommentRepository.save(BoardCommentEntity(board = findByIdOrThrow(boardComment.boardId), body = boardComment.body))
    }
}