import React from 'react';
import './App.css';

import { history } from './_helpers';
import {BrowserRouter, Route, Router } from "react-router-dom";
import MainLayout from "./layouts/MainLayout";
import { LoginPage } from './LoginPage';
import { PrivateRoute } from './_components';

function App() {
    return (
        <Router history={history}>
            <PrivateRoute path="/" exact component={MainLayout}/>
            <Route path="/login" exact component={LoginPage}/>
            <PrivateRoute path="/all*" exact component={MainLayout}/>
            <PrivateRoute path="/add*" exact component={MainLayout}/>
            <PrivateRoute path="/base*" exact component={MainLayout}/>
            <PrivateRoute path="/rule*" exact component={MainLayout}/>
            <PrivateRoute path="/policy*" exact component={MainLayout}/>
        </Router>
    );
}

export default App;
