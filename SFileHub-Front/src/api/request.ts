import axios from 'axios'
// axios.defaults.withCredentials = true

export const Service = axios.create({
    // headers: {
    //     "Content-Type": "application/json"
    // },
    timeout: 3000,
})

Service.defaults.headers.post['Content-Type'] = "application/json";

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