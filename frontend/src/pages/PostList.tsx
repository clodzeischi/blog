import {BlogCard} from "../components/BlogCard.tsx";
import {Button, Container} from "reactstrap";
import {useEffect, useState} from "react";
import {NewBlog} from "../components/NewBlog.tsx";
import {api} from "../client.ts"
import type {BlogpostProps} from "../types/BlogpostProps.ts";

export const PostList = () => {

    const [newBlogOpen, setNewBlogOpen] = useState(false);
    const [posts, setPosts] = useState<BlogpostProps[]>([]);

    const handleNewPost = (newPost: {author: string, subject: string, content: string}) => {
        api.post('/posts', newPost)
            .then(res => setPosts(prev => [res.data, ...prev]))
            .catch((error) => {console.error("Failed to create new post.", error)});
    }

    useEffect( () => {
        api.get('/posts')
            .then((res) => setPosts(res.data))
            .catch((error) => {console.error("Failed to get posts.", error)});
    }, []);

    return (
        <>
            <Container className='flex-column'>
                <Button className='m-3' onClick={ () => {setNewBlogOpen(true)} }>New post</Button>

                {posts.map(post => (
                    <BlogCard key={post.id} id={post.id} timestamp={post.timestamp} author={post.author}
                              subject={post.subject} content={post.content} likes={post.likes} />
                ))}
            </Container>

            <NewBlog modal={newBlogOpen} toggle={() => {setNewBlogOpen(!newBlogOpen)}}
                     onSubmit={handleNewPost}/>
        </>
    )
}