import { MockMethod } from 'vite-plugin-mock';
export default [
    {
        url: '/api/file/list',
        method: 'get',
        response: () => {
            return {
                code: 0,
                data: [
                    {
                        id: 1,
                        fileName: '文件1',
                        fileType: 'pdf',
                        fileSize: '1.2M',
                    },
                    {
                        id: 2,
                        fileName: '文件2',
                        fileType: 'pdf',
                        fileSize: '1.2M',
                    },
                ]
            }
        }
    }
] as MockMethod[];