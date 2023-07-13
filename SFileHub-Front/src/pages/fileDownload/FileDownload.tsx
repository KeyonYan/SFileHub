import React, { useState, useEffect } from "react";
import { queryFileList, get } from "@/api";
import { message } from 'antd';

interface FileType {
    identifier: string,
    fileName: string,
    suffix: string,
    fileType: string,
    size: number,
    createBy: string,
}

const FileList: React.FC = () => {
    const [messageApi] = message.useMessage();
    const [fileList, setFileList] = useState<FileType[]>([]); 
    useEffect(() => {
        queryFileList()
        .then(res => {
            if (res.code === 0) {
                setFileList(res.data);
            } else {
                messageApi.error(res.msg);
            }
        });
        return () => {
            setFileList([]);
        }
    }, [messageApi]);

    
    return (
        <ul>
            {fileList.map((item, index) => ( 
                <li key={index}>{item.fileName}</li>
            ))}
        </ul>
    );
}

const FileDownload: React.FC = () => {
    queryFileList()
    .then(res => {
        console.log(res)
    });
    return (
        <>
            <h1 className="flex justify-center text-xl font-semibold leading-6 text-gray-900">
                🗃️ FileDownloadPage
            </h1>
            <FileList />
        </>
    )
}

export default FileDownload;