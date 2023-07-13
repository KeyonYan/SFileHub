import React, { useState, useEffect } from "react";
import { queryFileList, downloadFile } from "@/api";
import { message } from 'antd';
import { FileSizeFormatter } from "@/util/format";
import { SaveOutlined, UserOutlined, DownloadOutlined } from '@ant-design/icons';

interface FileInfo {
    identifier: string,
    fileName: string,
    suffix: string,
    fileType: string,
    size: number,
    createBy: string,
}
type FileCardProps = {
    fileInfo: FileInfo,
}

const FileCard: React.FC<FileCardProps> = (props) => {
    const { fileInfo } = props;
    type CardTitleProps = {
        fileType: string,
        fileName: string,
    }
    type FileIconProps = {
        fileType: string,
    }
    const FileIcon: React.FC<FileIconProps> = ({fileType}) => {
        if (fileType === 'IMAGE') {
            return (
                <span>🖼️</span>
            )
        } else if (fileType === 'VIDEO') {
            return (
                <span>🎥</span>
            )
        } else if (fileType === 'AUDIO') {
            return (
                <span>🎵</span>
            )
        } else if (fileType === 'DOCUMENT') {
            return (
                <span>📄</span>
            )
        } else {
            return (
                <span>🗃️</span>
            )
        }
    }
    const CardTitle: React.FC<CardTitleProps> = ({fileType, fileName}) => {
        return (
            <div className="flex flex-row text-lg">
                <FileIcon fileType={fileType} />
                <p className=" text-slate-900 group-hover:text-red-500">{fileName}</p>
            </div>
        )
    }
    type CardInfoProps = {
        fileSize: number,
        createBy: string,
        downloadCount: number,
    }
    const CardInfo: React.FC<CardInfoProps> = ({fileSize, createBy, downloadCount}) => {
        return (
            <div className="flex flex-row items-center pl-1">
                <SaveOutlined className=" text-slate-500 pr-0.5" />
                <span className="text-sm text-slate-500">{FileSizeFormatter(fileSize)}</span>
                <span className="px-1 text-gray-300">•</span>
                <UserOutlined className="text-slate-500 pr-0.5" />
                <span className="text-sm text-slate-500">{createBy} </span>
                <span className="px-1 text-gray-300">•</span>
                <DownloadOutlined className="text-slate-500 pr-0.5"/>
                <span className="text-sm text-slate-500">{downloadCount}</span>
            </div>
        )
    }
    return (
        <div onClick={() => downloadFile(fileInfo.identifier)} className="group flex flex-col drop-shadow-md bg-gray-50 opacity-80 rounded-md p-5">
            <CardTitle fileType={fileInfo.fileType} fileName={fileInfo.fileName}/>
            <CardInfo fileSize={fileInfo.size} createBy={fileInfo.createBy} downloadCount={7}/>
        </div>
    )
}

const FileList: React.FC = () => {
    const [messageApi] = message.useMessage();
    const [fileList, setFileList] = useState<FileInfo[]>([]); 
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
        <div className="grid gap-4 grid-cols-2">
            {fileList.map((item, index) => ( 
                <FileCard fileInfo={item} key={index}/>
            ))}
        </div>
    );
}

const FileDownload: React.FC = () => {
    queryFileList()
    .then(res => {
        console.log(res)
    });
    return (
        <FileList />
    )
}

export default FileDownload;