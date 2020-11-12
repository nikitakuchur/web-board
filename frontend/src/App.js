import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import MainPage from "./pages/MainPage";
import BoardPage from "./pages/BoardPage";
import {BrowserRouter, Route} from "react-router-dom";

import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-bootstrap-range-slider/dist/react-bootstrap-range-slider.css';

class App extends Component {
    render() {
        return (
            <BrowserRouter>
                <Route
                    exact path="/"
                    component={MainPage}/>
                <Route
                    path="/:id"
                    component={BoardPage}/>
            </BrowserRouter>
        )
    }
}

export default App;
