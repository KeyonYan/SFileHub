import React from "react";
import type { UploadProps } from 'antd';
import { Button, Upload, Divider, Form, InputNumber, Switch } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import { upload } from "@/api/index";
import SparkMD5 from "spark-md5";

const FileUpload: React.FC = () => {
  const [config, contentHelper] = useUploadConfig({
    retry: { count: 1, delay: 3000 },
    isChunk: true,
    chunkSize: 100 * 1024,
  });

  const { chunkSize, isChunk, retry } = config;

  const getTotalChunks = (file: File) => Math.ceil(file.size / chunkSize);
  const getChunkData = (file: File, chunkIndex: number) => {
    const start = chunkIndex * chunkSize;
    const end = Math.min(start + chunkSize, file.size);
    return file.slice(start, end);
  };
  const getFileMD5 = (file: File) => {
    return new Promise((resolve) => {
      const blobSlice = File.prototype.slice;
      const spark = new SparkMD5.ArrayBuffer();
      const fileReader = new FileReader();
      const chunks = Math.ceil(file.size / chunkSize);
      let currentChunk = 0;
      fileReader.onload = function (e) {
        spark.append(e.target?.result as ArrayBuffer);
        if (currentChunk < chunks) {
          loadNext();
          currentChunk++;
        } else {
          const md5 = spark.end();
          resolve(md5);
        }
      };
      const loadNext = () => {
        const start = currentChunk * chunkSize;
        const end = Math.min(start + chunkSize, file.size);
        fileReader.readAsArrayBuffer(blobSlice.call(file, start, end));
      }
      loadNext();
    })
  }

  const customRequest: UploadProps['customRequest'] = async (options) => {
    const { onProgress, onError, onSuccess } = options;
    const file: File = options.file as File;
    const totalChunks = isChunk ? getTotalChunks(file as File) : 1;
    console.log("totalChunks: ", totalChunks);
    let uploadedChunks = 0;
    let isSuccess = true;

    const md5 = await getFileMD5(file) as string;
    for (let i = 0; i < totalChunks; i++) {
      const chunkData = getChunkData(file as File, i);
      console.log(chunkData);
      const postParams = {
        filename: file.name,
        chunkNumber: (i+1),
        chunkSize: chunkSize,
        currentChunkSize: file.size,
        totalSize: file.size,
        totalChunks: totalChunks,
        identifier: md5, // use md5 as identifier
        chunkFile: chunkData as Blob
      }
      try {
        // const result = await upload(postParams);
        const result = await requestWithRetry(postParams, retry);
        if (result?.code === 0) {
          uploadedChunks++;
          onProgress?.({ percent: (uploadedChunks / totalChunks) * 100 });
        } else {
          isSuccess = false;
          onError?.(result.msg);
          break;
        }
      } catch (error) {
        isSuccess = false;
        break;
      }
    }
    if (isSuccess) {
      onSuccess?.(file);
    }


    
  };

  return (
    <div>
      {contentHelper}
      <Divider orientation="center">Preview</Divider>
      <Upload customRequest={customRequest}>
        <Button icon={<UploadOutlined />}>Click to Upload</Button>
      </Upload>
    </div>
  );
};

type RetryOptions = {
  count: number;
  delay: number;
};

const requestWithRetry = async (params: any, options?: RetryOptions): Promise<any> => {
  const retryCount = options?.count || 3;
  const retryDelay = options?.delay || 3000;

  let error = null;

  for (let i = 0; i <= retryCount; i++) {
    try {
      const res = await upload(params);
      if (res.code === 0) return res;
    } catch (e) {
      error = e;
    }

    if (i < retryCount) {
      await new Promise((resolve) => {
        setTimeout(resolve, retryDelay);
      });
    }
  }

  throw error;
}

type Config = {
  retry: RetryOptions,
  isChunk: boolean;
  chunkSize: number;
}

function useUploadConfig(config: Config) {
  const [state, setState] = React.useState(config);
  const [form] = Form.useForm();

  const inputChunkSize = Form.useWatch('chunkSize', form);
  const [size, sizeM, sizeK] = React.useMemo(() => {
    const _size = inputChunkSize || 0;
    const M = Number.prototype.toFixed.call(_size / 1024 / 1024, 2);
    const K = Number.prototype.toFixed.call(_size / 1024, 2);
    return [_size, M, K];
  }, [inputChunkSize]);

  const contentHelper = (
    <Form form={form} initialValues={state} onValuesChange={(_, allValues) => setState(allValues)}>
      <Form.Item label="IsChunk" name="isChunk" valuePropName='checked'>
        <Switch />
      </Form.Item>
      <Form.Item
        label="ChunkSize(byte)"
        name="chunkSize"
        help={`${size}byte ≈ ${sizeK}kb ≈ ${sizeM}mb`}
      >
        <InputNumber
          disabled={!state.isChunk}
          min={10 * 1024}
          max={10 * 1024 * 1024}
          step={10 * 1024}
        />
      </Form.Item>
      <Form.Item label="retry" name={['retry', 'count']}>
        <InputNumber min={1} max={10} />
      </Form.Item>
      <Form.Item label="retry(ms)" name={['retry', 'delay']}>
        <InputNumber min={100} max={30 * 1000} step={200} />
      </Form.Item>
    </Form>
  );

  return [state, contentHelper] as const;
}

export default FileUpload;