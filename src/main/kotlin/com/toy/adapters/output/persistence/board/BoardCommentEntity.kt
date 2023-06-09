package com.toy.adapters.output.persistence.board

import com.toy.adapters.output.persistence.BaseEntity
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

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

    @CreatedBy
    @Column(updatable = false)
    val createdBy: String? = "1",

    @CreatedDate
    @Column(updatable = false)
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @LastModifiedBy
    val modifiedBy: String? = "1",

    @LastModifiedDate
    val modifiedAt: LocalDateTime? = LocalDateTime.now(),

    )//: BaseEntity()