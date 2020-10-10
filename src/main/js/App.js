import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import MainPage from "./pages/MainPage";
import CreateBoardPage from "./pages/CreateBoardPage";
import BoardPage from "./pages/BoardPage";
import {BrowserRouter, Route} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';

class App extends Component {
    render() {
        return (
            <BrowserRouter>
                <Route
                    exact path="/"
                    render={(props) => <MainPage {...props}/>}/>
                <Route
                    path="/create-board"
                    render={(props) => <CreateBoardPage {...props}/>}/>
                <Route
                    path="/board"
                    render={(props) => <BoardPage {...props}/>}/>
            </BrowserRouter>
        )
    }
}

ReactDOM.render(<App/>, document.getElementById('root'));
