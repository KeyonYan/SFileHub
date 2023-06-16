import { Service } from "./request"

interface LoginParams {
    username: string;
    password: string;
}

export function login(params: LoginParams) {
    return Service({
        url: "/api/login",
        data: params,
    })
}

export function hello() {
    return Service({
        url: "/api/hello",
        method: 'GET'
    })
}