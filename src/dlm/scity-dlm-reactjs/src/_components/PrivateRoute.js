// import { faNetworkWired } from '@fortawesome/free-solid-svg-icons';
import React from 'react';
import { Route, Redirect } from 'react-router-dom';

export const PrivateRoute = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => (
        checkUser()
            ? <Component {...props} />
            : <Redirect to={{ pathname: '/login', state: { from: props.location } }} />
    )
    } />
)

function checkUser(){
    var token = JSON.parse(sessionStorage.getItem('user'));
    var result = false;
    if(token){
        if(token.access_token){
            return true;
        }
    }
    return result;
}


function checkJwtExpiredDate(){
    const jwt = sessionStorage.getItem('user');
    const expiredDate = jwt.expires_in;
    if( expiredDate > Date.now() ){
        return sessionStorage.getItem('user');
    }
    else{
        return sessionStorage.getItem('user');;
    }
}