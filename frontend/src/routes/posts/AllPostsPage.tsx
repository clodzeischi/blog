import {createPost, getPosts} from "@/api/posts.ts"
import {useLoaderData} from "react-router";
import type {Post} from "@/types/Post.ts";
import {PostCard} from "@/components/PostCard.tsx";

export const allPostsLoader = async () => {
    return await getPosts()
}

export const AllPostsPage = () => {
    const posts = useLoaderData() as Post[]

    return (
        <div className={"space-y-6"}>
            {posts.map( p => (
                <PostCard key={p.id} post={p}/>
            ))}
        </div>
    )
}

export const createPostAction = async ( {req} : {req: Request}) => {
    const formData = await req.formData()
    const newPost = {
        title: formData.get("title") as string,
        content: formData.get("content") as string,
    }

    await createPost(newPost)
    return null
}