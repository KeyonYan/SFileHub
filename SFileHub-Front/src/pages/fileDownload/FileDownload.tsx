import React, { useState, useEffect } from "react";
import { queryFileList } from "@/api";
import { message, Card, Space } from 'antd';
import { FileSizeFormatter } from "@/util/format";

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
        <Space direction="vertical" size={16}>
            {fileList.map((item, index) => ( 
                <Card key={index} title={item.fileName}>
                    <ul className="flex flex-col">
                        <li>identifier: {item.identifier}</li>
                        <li>fileName: {item.fileName}</li>
                        <li>size: {FileSizeFormatter(item.size)}</li>
                        <li>createBy: {item.createBy} </li>
                    </ul>
                </Card>
            ))}
        </Space>
    );
}

const FileDownload: React.FC = () => {
    queryFileList()
    .then(res => {
        console.log(res)
    });
    return (
        <div className="flex flex-col">
            <h1 className="text-xl font-semibold leading-6 text-gray-900">
                🗃️ FileDownloadPage
            </h1>
            <FileList />
        </div>
    )
}

export default FileDownload;