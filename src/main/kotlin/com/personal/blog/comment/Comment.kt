package com.personal.blog.comment

import com.personal.blog.post.Post
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
class Comment (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @Column(nullable = false)
    val parentPost: Post,

    val text: String,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    val created: Instant = Instant.now()
)