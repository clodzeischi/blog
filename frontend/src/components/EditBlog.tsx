import {Button, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";
import {useState} from "react";

type editBlogProps = {
    text: string,
    modal: boolean,
    toggle: () => void
}

export const EditBlog = ({text, modal, toggle} : editBlogProps) => {

    const [formText, setFormText] = useState(text);

    return (
        <Modal isOpen={modal} toggle={toggle}>
            <ModalHeader toggle={toggle}>Edit post</ModalHeader>
            <ModalBody>
                <FormGroup>
                    <Label for="amount">Author</Label>
                    <Input type="text" id="text" value={formText}
                           onChange={e => setFormText(e.target.value)} />
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