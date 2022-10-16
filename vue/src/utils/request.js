/*引入axios*/
import axios from 'axios'
import {getToken} from "@/assets/token/token";


// 是否显示重新登录
export let isRelogin = {show: false};

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'

const request = axios.create({
    baseURL: 'http://localhost:8082', // 基础路径,将统一的部分全部封装
    timeout: 10000,
    withCredentials: true
})

// request请求拦截器
request.interceptors.request.use(config => {
        console.log("进入request拦截器")
        const token = getToken()
        if (token) {
            config.headers.Authorization = 'Bearer ' + token;
        }

        return config;
    }, error => Promise.reject(error)
);

//前端采用export.default，在写后端代码时用module.export
export default request

export function get(url){
    return function (params){
        return axios.get(url,{
            params
        })
    }
}
