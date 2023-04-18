package com.toy.adapters.output.persistence.board

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardCommentRepository : JpaRepository<BoardCommentEntity, String> {

}