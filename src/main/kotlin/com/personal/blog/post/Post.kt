package com.personal.blog.post

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant

@Entity
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    var title: String,
    var content: String,
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    val created: Instant = Instant.now(),

    @LastModifiedDate
    @Column(nullable = false)
    var updated: Instant = Instant.now()
)