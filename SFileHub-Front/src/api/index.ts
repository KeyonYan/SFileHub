import { Service } from "./request"

interface LoginParams {
    username: string;
    password: string;
    remember: boolean;
}

export function login(params: LoginParams) {
    return Service({
        url: "/api/login",
        data: params,
    })
}

export function hello() {
    return Service({
        url: "/api/user/hello",
        method: 'GET'
    })
}