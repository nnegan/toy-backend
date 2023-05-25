package com.toy.adapters.output.persistence

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate

import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class) // CreatedDate, LastModifiedDate 자동으로 들어감 -> boot 실행에 EnableJpaAuditing 어노테이션 넣어줘야됨
abstract class BaseEntity (

    @CreatedBy
    @Column( name="created_by", updatable = false)
    val createdBy: String = "1",

    @CreatedDate
    @Column(name="created_at", updatable = false)
    val createdAt: LocalDateTime,

    @LastModifiedBy
    val modifiedBy: String = "1",

    @LastModifiedDate
    val modifiedAt: LocalDateTime,
)