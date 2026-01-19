import {
    Card,
    CardAction,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle
} from "@/components/ui/card.tsx";
import type {Post} from "@/types/Post.ts";

export const CommentCard = ( {post} : {post: Post}) => {
    return (
        <Card>
            <CardHeader>
                <CardTitle>{post.title}</CardTitle>
                <CardDescription>{post.created}</CardDescription>
                <CardAction>Card Action</CardAction>
            </CardHeader>
            <CardContent>
                <p>{post.content}</p>
            </CardContent>
            {(post.created !== post.updated) && <CardFooter>
                <p>Last edited: {post.updated}</p>
            </CardFooter>}
        </Card>
    )
}