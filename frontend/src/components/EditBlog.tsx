import {Button, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";
import {useEffect, useState} from "react";

type editBlogProps = {
    modal: boolean,
    toggle: () => void,
    initialText: string,
    onSubmit: (content: string) => void
}

export const EditBlog = ({modal, toggle, initialText, onSubmit} : editBlogProps) => {

    const [content, setContent] = useState(initialText);

    useEffect(() => {
        if (modal) setContent(initialText)
    }, [modal, initialText]);

    const submit = () => {
        onSubmit(content);
        toggle();
    }

    return (
        <Modal isOpen={modal} toggle={toggle}>
            <ModalHeader toggle={toggle}>Edit post</ModalHeader>
            <ModalBody>
                <FormGroup>
                    <Label for="amount">Author</Label>
                    <Input type="text" id="text" value={content}
                           onChange={e => setContent(e.target.value)} />
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