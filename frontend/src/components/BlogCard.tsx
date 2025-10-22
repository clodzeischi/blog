import {Card, CardBody, CardImg, CardText, CardTitle} from "reactstrap";
import type {BlogpostProps} from "../types/BlogpostProps.ts";
import {Link} from "react-router-dom";

export const BlogCard = (props: BlogpostProps) => {
    return (
        <Link to={`/posts/${props.id}`}>
            <Card className="my-2">
                <CardImg
                    alt="Card image cap"
                    src="/bk.jpg"
                    style={{
                        height: 180,
                        objectFit: 'cover'
                    }}
                    top
                    width="100%"
                />
                <CardBody>
                    <CardTitle tag="h5">
                        {props.subject}
                    </CardTitle>
                    <CardText>
                        {props.content}
                    </CardText>
                    <CardText>
                        <small className="text-muted">
                            {props.timestamp}
                        </small>
                    </CardText>
                    <CardText>
                        <small className="text-muted">
                            Likes: {props.likes}
                        </small>
                    </CardText>
                </CardBody>
            </Card>
        </Link>
    )
}

