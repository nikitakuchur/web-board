import React, {Component} from 'react';
import BoardsPage from "./pages/BoardsPage";
import LoginPage from "./pages/LoginPage";
import BoardPage from "./pages/BoardPage";
import {BrowserRouter, Route, Redirect} from "react-router-dom";

import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-bootstrap-range-slider/dist/react-bootstrap-range-slider.css';

class App extends Component {
    render() {
        return (
            <BrowserRouter>
                <Route exact path="/" render={() => (<Redirect to="/boards"/>)}/>
                <Route
                    exact path="/boards"
                    component={BoardsPage}/>
                <Route
                    path="/login"
                    component={LoginPage}/>
                <Route
                    path="/boards/:id"
                    component={BoardPage}/>
            </BrowserRouter>
        );
    }
}

export default App;
