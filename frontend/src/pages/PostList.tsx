import {BlogCard} from "../components/BlogCard.tsx";
import {Button, Container} from "reactstrap";
import {useState} from "react";
import {NewBlog} from "../components/NewBlog.tsx";

export const PostList = () => {

    const [newBlogOpen, setNewBlogOpen] = useState(false);

    return (
        <>
            <Container className='flex-column'>
                <Button className='m-3' onClick={ () => {setNewBlogOpen(true)} }>New post</Button>
                <BlogCard
                    id={1}
                    timestamp='20250405'
                    author='Generic Author'
                    subject='My exciting subject'
                    content='This block contains all of my deepest thoughts on this subject.'
                    likes={28}/>
                <BlogCard
                    id={1}
                    timestamp='20250405'
                    author='Generic Author'
                    subject='My exciting subject'
                    content='This block contains all of my deepest thoughts on this subject.'
                    likes={28}/>
            </Container>

            <NewBlog modal={newBlogOpen} toggle={() => {setNewBlogOpen(!newBlogOpen)} }/>
        </>

    )
}