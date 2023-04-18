package com.toy.adapters.output.persistence.board

import jakarta.persistence.*

@Entity
@Table(name = "board_comment")
class BoardCommentEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    val board: BoardEntity,

    val body: String,

)