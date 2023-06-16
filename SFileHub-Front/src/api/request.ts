import axios from 'axios'

export const Service = axios.create({
    timeout: 8000,
    method: 'POST',
    headers: {
        "content-Type": "application/x-www-form-urlencoded",
        // "pc-token": ""
    }
})

Service.interceptors.request.use(config => {
    return config;
})

Service.interceptors.response.use(response => {
    return response.data;
}, err => {
    console.log(err);
})