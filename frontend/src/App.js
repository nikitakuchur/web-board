import React, {Component} from 'react';
import LogInPage from "./pages/LogInPage";
import SignUpPage from "./pages/SignUpPage";
import BoardListPage from "./pages/BoardListPage";
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
                    path="/login"
                    component={LogInPage}/>
                <Route
                    path="/signup"
                    component={SignUpPage}/>
                <Route
                    exact path="/boards"
                    component={BoardListPage}/>
                <Route
                    path="/boards/:id"
                    component={BoardPage}/>
            </BrowserRouter>
        );
    }
}

export default App;
