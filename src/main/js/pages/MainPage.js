import React, {Component} from 'react';
import {Link} from "react-router-dom";

class MainPage extends Component {
    render() {
        return (
            <React.Fragment>
                <h4>Main page</h4>
                <Link to={{ pathname: '/board' }}>Go to board</Link>
            </React.Fragment>);
    }
}

export default MainPage;