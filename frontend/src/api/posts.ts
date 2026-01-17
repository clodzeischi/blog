import { API_BASE } from "../config"
import type {Post} from "../types/Post.ts"

export async function createPost(post: Post) {
    const res = await fetch(`${API_BASE}/posts`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(post)
    })

    if (!res.ok) {
        throw new Error(`Failed to create post: ${res.status}`)
    }

    return res.json()
}

export async function getPosts() {
    const res = await fetch(`${API_BASE}/posts`)

    if (!res.ok) {
        throw new Error(`Failed to get posts: ${res.status}`)
    }

    return res.json()
}

export async function getPostByID(id: number) {
    const res = await fetch(`${API_BASE}/posts/${id}`)

    if (!res.ok) {
        throw new Error(`Failed to get post: ${res.status}`)
    }

    return res.json()
}

export async function updatePost(post: Post) {
    const res = await fetch(`${API_BASE}/posts`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(post)
    })

    if (!res.ok) {
        throw new Error(`Failed to update post: ${res.status}`)
    }

    return res.json()
}

export async function deletePost(id: number) {
    const res = await fetch(`${API_BASE}/posts/${id}`,
        { method: "DELETE" })

    if (!res.ok) {
        throw new Error(`Failed to delete post: ${res.status}`)
    }

    return { status: "204" }
}
