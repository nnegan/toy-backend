package com.toy.adapters.output.persistence.user

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime


@Entity
@Table(name = "user_info")
class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userNo: Long = 0,

    @Column
    val userId: String = "",

    @Column
    val userPassword: String = "",

    @Column
    val userName: String = "",

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

) {
}