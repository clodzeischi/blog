import {Button, Col, Container, Input, Row} from "reactstrap";
import {useState} from "react";

export const Comments = ({postID} : {postID: number}) => {

    const [comments, setComments] = useState([]);

    function getCommentsByPostID() {}

    function deleteCommentByID(id: number) {}

    return (
        <>
            <Container className="p-3 bg-light border container-fluid">
                <Row className="g-2 align-items-center">
                    <Col md="2">
                        <Input type="text" placeholder="Username" />
                    </Col>
                    <Col md="8">
                        <Input type="text" placeholder="Comment" />
                    </Col>
                    <Col md="2">
                        <Button color="primary" block>
                            Submit
                        </Button>
                    </Col>
                </Row>
            </Container>

            All comments would go here. {postID}
        </>
    )
}