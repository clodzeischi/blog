import {Button, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";
import {useState} from "react";

type newBlogProps = {
    modal: boolean,
    toggle: () => void
}

export const NewBlog = ({modal, toggle} : newBlogProps) => {

    const [author, setAuthor] = useState('');
    const [subject, setSubject] = useState('');
    const [text, setText] = useState('');

    return (
        <Modal isOpen={modal} toggle={toggle}>
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
                    <Input type="text" id="comment" value={text}
                       onChange={e => setText(e.target.value)} />
                </FormGroup>
            </ModalBody>
            <ModalFooter>
                <Button color="primary" onClick={toggle}>
                    Confirm
                </Button>{' '}
                <Button color="secondary" onClick={toggle}>
                    Cancel
                </Button>
            </ModalFooter>
        </Modal>
    )
}