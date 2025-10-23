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
        <Modal isOpen={modal} toggle={toggle} role='dialog'>
            <ModalHeader toggle={toggle}>New post</ModalHeader>
            <ModalBody>
                <FormGroup>
                    <Label for="amount">Author</Label>
                    <Input type="text" id="text" value={author}
                       onChange={e => setAuthor(e.target.value)} />
                </FormGroup>
                <FormGroup>
                    <Label for="comment">Subject</Label>
                    <Input type="text" id="comment" value={subject}

                       onChange={e => setSubject(e.target.value)} />
                </FormGroup>
                <FormGroup>
                    <Label for="comment">Text</Label>
                    <Input type="text" id="comment" value={content}
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