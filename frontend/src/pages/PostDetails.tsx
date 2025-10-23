import {useParams} from "react-router-dom";
import {Button, Container} from "reactstrap";
import {useEffect, useState} from "react";
import {EditBlog} from "../components/EditBlog.tsx";
import {Comments} from "../components/Comments.tsx";
import type {BlogpostProps} from "../types/BlogpostProps.ts";
import {api} from "../client.ts";

export const PostDetails = () => {
    const {id} = useParams();

    const [post, setPost] = useState<BlogpostProps | null>(null);
    const [editOpen, setEditOpen] = useState(false);

    useEffect(() => {
        if (id) {
            api.get(`/posts/${id}`)
                .then(res => setPost(res.data))
                .catch((error) => { console.error("Unable to find post.", error)});
        }
    }, [id]);

    const like = () => {
        if (id) {
            api.post(`/posts/${id}/like`)
                .then(res => setPost(res.data))
                .catch((error) => { console.error("Unable to process like.", error)})
        }
    }

    const submitEdit = (body: string) => {
        if (id) {
            api.patch(`/posts/${id}`, {content: body})
                .then(res => {
                    setPost(prev => prev ? { ...prev, content: res.data.content } : prev);
                    setEditOpen(false);
                })
                .catch((error) => {console.error("Unable to submit edit.", error)});
        }
    }

    const handleDelete = () => {
        api.delete(`/posts/${id}`)
            .then(() => {
                window.location.href = '/'; // redirect to main page
            })
            .catch(err => console.error('Failed to delete post:', err));
    };

    return (
        <>
            <img
                alt='Background'
                src='/bk.jpg'
                style={{
                    objectFit: 'cover'
                }}
                height='160'
                width='100%'
            />
            <Container className='flex-column'>
                <div>
                    <Button className='m-3' href='/'>Back</Button>
                    <Button className='m-3' onClick={() => {setEditOpen(true)}}>Edit</Button>
                    <Button className='m-3 btn-danger' onClick={handleDelete}>Delete</Button>
                </div>

                <h2>{post?.subject}</h2>
                <h3 className='text-muted'>{post?.author}</h3>
                <p>{post?.content}</p>

                <div>
                    <Button className='m-3 btn-dark' onClick={like}>Like</Button>
                    <span className='m-3'>Likes: {post?.likes}</span>
                </div>

                <Comments postID={Number(id)}/>
            </Container>

            <EditBlog modal={editOpen} initialText={post?.content}
                      toggle={() => {setEditOpen(!editOpen)}}
                        onSubmit={submitEdit}/>
        </>
    )
}