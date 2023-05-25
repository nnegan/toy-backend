package com.toy.adapters.output.persistence.board

import com.toy.adapters.output.persistence.BaseEntity
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.sql.Timestamp
import java.time.LocalDateTime

import java.time.format.DateTimeFormatter


@Entity
@Table(name = "board")
class BoardEntity (

    @Id
    val boardId: String,

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) //mappedBy = "BoardCommentEntity.boardId",
    val comments: MutableList<BoardCommentEntity> = mutableListOf(),

    val title: String,

    val body: String,

    val useYn: String,

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
