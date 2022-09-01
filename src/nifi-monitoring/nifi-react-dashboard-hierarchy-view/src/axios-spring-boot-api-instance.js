import axios from 'axios';

/*
*
* Define base URL
* */
const axios_spring_boot_api_instance = axios.create({
    // baseURL: 'http://210.115.182.181:9990/api/v1',
    // baseURL: 'http://localhost:9990/api/v1',
    baseURL: window._env_.API_URL + window._env_.API_PORT + '/api/v1',
    // baseURL: 'http://133.186.162.192:18001/api/v1',
    headers: {
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': '*',
        // 'Content-Type': 'application/json',
    },

    withCredentials: true
});

export default axios_spring_boot_api_instance;
