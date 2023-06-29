import React from "react";
import { get, put, post } from "../../api/index";
import { Button } from "antd"

const getHandle = () => {
    const res = get();
    res.then((res) => {
        alert(res);
        console.log(res);
    });
}

const putHandle = () => {
    const res = put();
    res.then((res) => {
        alert(res.msg);
        console.log(res.msg);
    })
}

const postHandle = () => {
    const res = post();
    res.then((res) => {
        alert(res);
        console.log(res);
    })
}

const Dataset: React.FC = () => {
    return (
        <div className="flex flex-col space-y-6 justify-center items-center">
            <h1 className="flex justify-center text-xl font-semibold leading-6 text-gray-900">
                🗃️ Dataset
            </h1>
            <div className="flex-col space-x-6">
                <Button className="" onClick={getHandle}>Get</Button>
                <Button className="" onClick={putHandle}>Put</Button>
                <Button className="" onClick={postHandle}>Post</Button>
            </div>
        </div>
    )
}

export default Dataset;