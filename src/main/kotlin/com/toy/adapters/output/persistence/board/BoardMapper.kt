package com.toy.adapters.output.persistence.board


import com.toy.adapters.output.persistence.board.data.SearchBoardDto
import com.toy.domain.board.model.Board
import org.springframework.stereotype.Component

@Component
class BoardMapper{

    fun mapToEntity(board: Board) = with(board){
        BoardEntity(
            boardId = boardId!!,
            title = title,
            body = body,
            useYn = useYn,
            //createdBy = createdBy!!,
            //createdTime!!,
            //modifiedBy = modifiedBy!!,
            //modifiedTime!!
        )
    }


    fun copyToEntity(board: Board, boardEntity: BoardEntity) : BoardEntity {
        return BoardEntity(
            boardId = boardEntity.boardId,
            title = board.title,
            body = board.body,
            useYn = board.useYn,
        //    createdBy = board.createdBy!!,
        //  modifiedBy = board.modifiedBy!!
        )

    }

    fun mapToDomain(boardEntity: BoardEntity) = with(boardEntity){
        Board(
            boardId = boardId,
            title = title,
            body = body,
            useYn = useYn,
            createdBy = createdBy,
            createdAt = createdAt,
            modifiedBy = modifiedBy,
            modifiedAt = modifiedAt
        )
    }

    fun searchMapToDomain(searchBoardDto: SearchBoardDto) = with(searchBoardDto){
        Board(
            boardId = boardId,
            title = title,
            body = body,
            useYn = useYn,
            createdBy = createdBy,
            createdAt = createdAt,
            modifiedBy = modifiedBy,
            modifiedAt = modifiedAt
        )
    }

}