package com.toy.adapters.output.persistence.board


import com.toy.domain.board.model.Board
import com.toy.domain.board.model.SearchBoard
import org.springframework.data.domain.Page

interface BoardRepositoryCustom{
    fun searchBoardPageList(searchBoard: SearchBoard): Page<Board>

}