package com.personal.blog.comment

import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {

    fun findAllByParentPost_Id(parentPostId: Long): List<Comment>
    fun deleteAllByParentPost_Id(parentPostId: Long)
}