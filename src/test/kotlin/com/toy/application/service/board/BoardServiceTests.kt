package com.toy.application.service.board

import com.toy.application.port.input.board.BoardUseCase
import com.toy.application.port.output.repository.board.BoardPort
import com.toy.domain.board.model.SearchBoard
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory

@ExtendWith(MockKExtension::class)
class BoardServiceTests {

    @InjectMockKs
    private lateinit var boardUseCase: BoardUseCase

    private val log = LoggerFactory.getLogger(javaClass)

    @SpyK
    private lateinit var boardPort : BoardPort

    @Test
    fun 게시판조회(){
        val searchBoard : SearchBoard =
            SearchBoard(

            )

        val result = boardUseCase.searchPageBoardList(searchBoard)

        log.debug("result {}", result.toString())


    }

/*

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun findOneBoard(boardId: String): Board {
        return boardPort.findOneBoard(boardId)
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun searchPageBoardList(searchBoard: SearchBoard): Page<Board> {
        return boardPort.searchPageBoardList(searchBoard)
    }

    override fun createBoard(board: Board): String {
        val boardCopy = board.copy(boardId = redisSequence.getSequenceLeftPad8(BOARD_ID_PREFIX, BOARD_KEY))

        log.debug("boardCopy = {}", boardCopy.toString())


        return boardPort.createBoard(boardCopy)
    }

    override fun modifyBoard(apiId: String, board: Board): String {
        return boardPort.modifyBoard(apiId, board)
    }

    override fun deleteBoard(boardId: String) {
        return boardPort.deleteBoard(boardId)
    }*/

}
