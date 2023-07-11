import React, { useState, useEffect } from "react";
import { queryFileList } from "@/api";
import { message } from 'antd';

interface FileType {
    identifier: string,
    fileName: string,
    suffix: string,
    fileType: string,
    size: number,
    createBy: string,
}

const FileDownload: React.FC = () => {
    const [messageApi] = message.useMessage();
    const [fileList, setFileList] = useState<FileType[]>([]); 
    
    useEffect(() => {
        queryFileList()
            .then(res => {
                if (res.code != 0) {
                    messageApi.open({
                        type: 'error',
                        content: res.msg,
                    });
                }
                setFileList(res.data);
                console.log(fileList);
            });
    }, []);

    const listItems = fileList.map(file =>
        <li key={file.identifier}>
            {file.fileName}
        </li>
    );
    return (
        <>
            <h1 className="flex justify-center text-xl font-semibold leading-6 text-gray-900">
                🗃️ FileDownloadPage
            </h1>
            <ul>{listItems}</ul>
        </>
    )
}

export default FileDownload;