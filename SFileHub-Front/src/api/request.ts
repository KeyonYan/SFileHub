import axios from 'axios'
// axios.defaults.withCredentials = true

export const Service = axios.create({
    timeout: 3000,
})

export type RetryOptions = {
    count: number;
    delay: number;
  };

export const ServiceWithRetry = async (api: (p: any) => Promise<any>, retry: RetryOptions, params?: any) => {
    const {count, delay} = retry;
    let error = null;
    for (let i = 0; i < count; i++) {
        try {
            const res = await api(params);
            if (res.code === 0) return res;
          } catch (e) {
            error = e;
          }
          if (i < count) {
            await new Promise((resolve) => {
              setTimeout(resolve, delay);
            });
          }
    }
    throw error;
}

declare module 'axios' {
    interface AxiosInstance {
        (config: AxiosRequestConfig): Promise<any>
    }
}

// Service.defaults.headers.post['Content-Type'] = "application/json";

// 添加一个请求拦截器
Service.interceptors.request.use(function(config) {
    const uToken = localStorage.getItem("token");
    if(uToken){
      config.headers['Authorization'] = uToken;
    }
    return config;
  },error => {
    Promise.reject(error);
});

Service.interceptors.response.use(response => {
    return response.data;
}, err => {
    console.log(err);
})