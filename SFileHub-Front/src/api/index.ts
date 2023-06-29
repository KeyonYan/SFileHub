import { Service } from "./request"

interface LoginParams {
    username: string;
    password: string;
    remember: boolean;
}

export function login(params: LoginParams) {
    return Service({
        url: "/api/login",
        method: 'POST',
        data: params,
    })
}

export function get() {
    return Service({
        url: "/api/hello/get",
        method: 'GET'
    })
}

export function put() {
    return Service({
        url: "/api/hello/put",
        method: 'PUT'
    })
}

export function post() {
    return Service({
        url: "/api/hello/post",
        method: 'POST'
    })
}

export function upload(params: JSON) {
    return Service({
        url: "/api/file/upload",
        data: params,
        method: 'POST',
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}