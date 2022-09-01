import React from 'react';
import MainLayout from "./hoc/Layout/MainLayout";

import {BrowserRouter} from "react-router-dom";
import 'antd/dist/antd.css';

function App() {
    return (
        <BrowserRouter>
            {/*<div className={classes.App}>*/}
            <div>
                <MainLayout/>
            </div>

        </BrowserRouter>
    );
}

export default App;
