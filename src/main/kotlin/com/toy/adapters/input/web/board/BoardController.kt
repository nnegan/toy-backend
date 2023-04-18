package com.toy.adapters.input.web.board

import com.toy.adapters.input.web.board.data.*
import com.toy.framework.exception.ResourceNotFoundException
import com.toy.adapters.input.web.util.response.ResponseUtils
import com.toy.application.port.input.board.BoardUseCase
import com.toy.domain.board.model.Board
import com.toy.domain.board.model.BoardComment
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@Tag(name="[게시판-관리] 게시판 API", description = "게시판의 목록을 관리하기 위한 기능")
@RestController
@RequestMapping("/v1/board/api")
class BoardController (
    private val boardUseCase: BoardUseCase,
    private val boardDtoMapper: BoardDtoMapper,
) {

    private val log = LoggerFactory.getLogger(javaClass)

   /**
     * 게시판 목록 조회 (조회조건 및 Page 처리와 조건식)
     */
    @Operation( summary = "[API 조회] 리스트, 조회조건, 페이징 처리", description = "등록된 API 목록 전체를 조회한다.")
    @PostMapping("/search/pagelist")
    fun searchPageBoardList(@RequestBody searchPageBoardRequestDto: SearchPageBoardRequestDto): ResponseEntity<Page<Board>>{
        return try{
            ResponseUtils.searchResults(boardUseCase.searchPageBoardList(boardDtoMapper.searchPageRequestDtoToDomain(searchPageBoardRequestDto)))
        }catch(e: Exception){
            log.error("Exception : {}", e)
            ResponseUtils.failServerError() // 메시지 처리
        }
    }

    /**
     * 게시판 상세
     */
    @Operation( summary = "[게시판 조회] 단건", description = "등록된 게시판 하나의 게시판를 조회한다.")
    @GetMapping("/{boardId}")
    fun findOneBoard(@PathVariable("boardId") boardId: String) : ResponseEntity<BoardResponseDto>{
        return try{
            ResponseUtils.searchResults(boardDtoMapper.domainToResponseDto(boardUseCase.findOneBoard(boardId)))
        }catch(e: Exception){
            log.error("Exception : {}", e)
            ResponseUtils.failServerError() // 메시지 처리
        }
    }


    @Operation( summary = "[게시판 등록] 등록", description = "등록")
    @PostMapping("/create")
    fun createBoard(@RequestBody @Valid boardRequestDto: BoardRequestDto): ResponseEntity<Any> {
        return try{
            boardUseCase.createBoard(boardDtoMapper.requestDtoToDomain("", boardRequestDto, "USER")).let { boardId -> ResponseUtils.added(boardId) }
        }catch(e: Exception){
            log.error("Exception : {}", e)
            ResponseUtils.failServerError() // 메시지 처리
        }
    }

    @Operation( summary = "[게시판 수정] 수정", description = "수정")
    @PutMapping("/modify/{boardId}")
    fun modifyBoard(@PathVariable boardId: String, @RequestBody @Valid boardRequestDto: BoardRequestDto) : ResponseEntity<Any>{
        return try{
            boardUseCase.modifyBoard(boardId, boardDtoMapper.requestDtoToDomain("", boardRequestDto, "USER")).let { boardId -> ResponseUtils.modified(boardId) }
        }catch(e: Exception){
            log.error("Exception : {}", e)
            ResponseUtils.failServerError() // 메시지 처리
        }
    }

    @Operation( summary = "[게시판 삭제] 삭제", description = "삭제시에는 API를 참고하고 있는 화면과 외부시스템 권한을 검사한다")
    @DeleteMapping("/delete/{boardId}")
    fun deleteBoard(@PathVariable boardId: String):ResponseEntity<Any>  {
        return try{
            boardUseCase.deleteBoard(boardId).let { ResponseUtils.deleted() }
        }catch(e: ResourceNotFoundException){
            log.error("ResourceNotFoundException : {}", e)
            ResponseUtils.failBadRequest("삭제할 대상이 없습니다") // 메시지 처리
        }catch (e: Exception){
            log.error("Exception : {}", e)
            ResponseUtils.failServerError(e.message) // 메시지 처리
        }
    }

    @PostMapping("/create/{boardId}/comment")
    fun createComment(@PathVariable boardId: String, @RequestBody boardCommentRequestDto: BoardCommentRequestDto){
        val boardComment =  BoardComment(boardId = boardId, body = boardCommentRequestDto.body)
        boardUseCase.createComment(boardComment)

    }


    @GetMapping("/force/lock/{boardId}")
    fun forceLock(@PathVariable boardId: String){
        boardUseCase.forceLock(boardId)
    }

}