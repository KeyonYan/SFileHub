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
interface UploadParams {
    fileName: string;
    chunkNumber: number;
    chunkSize: number;
    currentChunkSize: number;
    totalSize: number;
    totalChunks: number;
    identifier: string;
    chunkFile: Blob;
}
export function upload(params: UploadParams) {
    return Service({
        url: "/api/file/upload",
        data: params,
        method: 'POST',
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

export function checkBeforeUpload(identifier: string) {
    return Service({
        url: "/api/file/uploadCheck?identifier=" + identifier,
        method: 'GET',
    })
}

export function queryFileList() {
    return Service({
        url: "/api/file/list",
        method: 'GET'
    })
}

export function downloadFile(identifier: string) {
    return Service({
        url: "/api/file/download/" + identifier,
        method: 'POST',
        headers: {
            responseType: 'blob'
        }
    })
}