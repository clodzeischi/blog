import { setupServer } from 'msw/node'
import { http, HttpResponse } from 'msw'
import { FALLBACK_API_BASE } from "../config.ts"
import type {Post} from "../types/Post.ts"

const responsePost: Post = {
    id:1,
    title: "Hello",
    content: "World",
    created: "2026-01-01",
    updated: "2026-01-01"
}

export const server = setupServer(

    http.post(`${FALLBACK_API_BASE}/posts`, async ({ request }) => {
        const body = await request.json()
        const post: Post = body as Post

        if (!post.title || !post.content) {
            return HttpResponse.json(
                { message: "Invalid request" },
                { status: 400 }
            )
        }

        return HttpResponse.json(responsePost, { status: 201 })
    }),

    http.get(`${FALLBACK_API_BASE}/posts`, () => {
        return HttpResponse.json([responsePost])
    }),

    http.get(`${FALLBACK_API_BASE}/posts/1`, () => {
        return HttpResponse.json(responsePost)
    }),

    http.get(`${FALLBACK_API_BASE}/posts/2`, () => {
        return HttpResponse.json({ message: "Post not found" }, { status: 404 })
    }),

    http.patch(`${FALLBACK_API_BASE}/posts`, async ({ request }) => {
        const body = await request.json()
        const post: Post = body as Post

        if (!post.title || !post.content) {
            return HttpResponse.json({ message: "Invalid request" }, { status: 400 })
        }

        if (post.id === 2) return HttpResponse.json({ message: "Post not found" }, { status: 404 })

        return HttpResponse.json(responsePost, { status: 201 })
    }),

    http.delete(`${FALLBACK_API_BASE}/posts/1`, () =>
        new HttpResponse(null, { status: 204 })),

    http.delete(`${FALLBACK_API_BASE}/posts/2`, () => {
        return HttpResponse.json({ message: "Post not found" }, { status: 404 })
    })
)
