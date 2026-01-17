import { describe, it, expect } from 'vitest'
import {createPost, deletePost, getPostByID, getPosts, updatePost} from './posts'
import type {Post} from "../types/Post.ts"

const requestPost: Post = {
    title: "Hello",
    content: "World"
}

describe('client', () => {
    it('should create post', async () => {
        const post = await createPost(requestPost)
        expect(post.title).toBe("Hello")
    })

    it('should get posts', async () => {
        const posts = await getPosts()
        expect(posts.length).toBe(1)
        expect(posts[0].title).toBe('Hello')
    })

    it('should get post by id', async () => {
        const post = await getPostByID(1)
        expect(post.title).toBe('Hello')
    })

    it('should not get post if not found', async () => {
        await expect(getPostByID(2)).rejects.toThrow()
    })

    it('should update post', async() => {
        const post = await updatePost(requestPost)
        expect(post.title).toBe("Hello")
    })

    it('should not update post if not found', async() => {
        requestPost.id = 2
        await expect(updatePost(requestPost)).rejects.toThrow()

    })

    it('should delete post', async() => {
        const res = await deletePost(1)
        expect(res.status).toBe('204')
    })

    it('should not delete post if not found', async() => {
        await expect(deletePost(2)).rejects.toThrow()
    })
})
