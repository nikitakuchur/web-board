import React, {Component} from 'react';
import {Card, Button} from "react-bootstrap";
import {Link} from "react-router-dom";

class MainPage extends Component {
    render() {
        return (
            <React.Fragment>
                <h4>Main page</h4>
                <Card style={{width: '18rem'}}>
                    <Card.Body style={{padding: '14px'}}>
                        <Card.Title>Board</Card.Title>
                        <Card.Text>The board description</Card.Text>
                        <Link to={{pathname: '/board'}}>
                            <Button variant="primary">Go to board</Button>
                        </Link>
                    </Card.Body>
                </Card>
            </React.Fragment>);
    }
}

export default MainPage;