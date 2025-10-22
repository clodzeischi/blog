import {useParams} from "react-router-dom";
import {Button, Container} from "reactstrap";
import {useState} from "react";
import {EditBlog} from "../components/EditBlog.tsx";

export const PostDetails = () => {
    const {id} = useParams();

    const [editOpen, setEditOpen] = useState(false);
    const [likes, setLikes] = useState(10);

    return (
        <>
            <img
                alt='Background'
                src='/bk.jpg'
                style={{
                    objectFit: 'cover'
                }}
                height='140'
                width='100%'
            />
            <Container className='flex-column'>
                <div>
                    <Button className='m-3' href='/'>Back</Button>
                    <Button className='m-3' onClick={() => {setEditOpen(true)}}>Edit</Button>
                </div>

                <h2>Post Details {id}</h2>

                <div>
                    <Button onClick={ () => {setLikes(likes + 1)}}>Like</Button>
                    Likes: {likes}
                </div>
            </Container>

            <EditBlog text="Generic text for now" modal={editOpen} toggle={() => {setEditOpen(!editOpen)} }/>
        </>

    )
}