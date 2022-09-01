import axios from 'axios';

/*
*
* Define base URL
* */
const axios_spring_boot_api_instance = axios.create({
    // baseURL: 'http://133.186.162.214:18009/api/',
    baseURL: window._env_.API_URL + window._env_.API_PORT + '/api/',
    // baseURL: 'http://210.115.182.180:18009/api/',
    headers: {
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': '*',
        // 'Content-Type': 'application/json',
    },

    withCredentials: true
});

export default axios_spring_boot_api_instance;
