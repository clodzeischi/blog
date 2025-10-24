import {Button, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";
import {useState} from "react";

type newBlogProps = {
    modal: boolean,
    toggle: () => void,
    onSubmit: (post: {author: string, subject: string, content: string}) => void
}

export const NewBlog = ({modal, toggle, onSubmit} : newBlogProps) => {

    const [author, setAuthor] = useState('');
    const [subject, setSubject] = useState('');
    const [content, setText] = useState('');

    const submit = () => {
        onSubmit({author, subject, content});
        setAuthor('');
        setSubject('');
        setText('');
        toggle();
    }

    return (
        <Modal isOpen={modal} toggle={toggle} role='dialog' aria-label='New blog post'>
            <ModalHeader toggle={toggle}>New blog post</ModalHeader>
            <ModalBody>
                <FormGroup>
                    <Label for="amount">Author</Label>
                    <Input type="text" id="text" value={author} aria-label='input-author'
                       onChange={e => setAuthor(e.target.value)} />
                </FormGroup>
                <FormGroup>
                    <Label for="subject">Subject</Label>
                    <Input type="text" id="subject" value={subject} aria-label='input-subject'
                       onChange={e => setSubject(e.target.value)} />
                </FormGroup>
                <FormGroup>
                    <Label for="content">Text</Label>
                    <Input type="text" id="content" value={content} aria-label='input-content'
                       onChange={e => setText(e.target.value)} />
                </FormGroup>
            </ModalBody>
            <ModalFooter>
                <Button color="primary" onClick={submit}>
                    Confirm
                </Button>{' '}
                <Button color="secondary" onClick={toggle}>
                    Cancel
                </Button>
            </ModalFooter>
        </Modal>
    )
}