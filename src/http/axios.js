/* 导入官方的axios */
import axios from "axios";
import config from "@/http/config";
import Cookies from "js-cookie";
const instance = axios.create({
    baseURL: config.baseUrl,
    timeout: config.timeOut,
    headers: config.headers,
    cookies: config.cookies,
    withCredentials: true
})

instance.interceptors.request.use(
    config => {
        /*
        请求之前，携带上token
         */
        let token = sessionStorage.getItem("token");
        if (token) {
            config.headers.token = token;
        }

        let userId = Cookies.get("userIdStr")
        if (userId) {
            config.headers.userIdStr = userId
        }

        let userName = Cookies.get("userName");
        if (userName){
            config.headers.userName = userName;
        }
        let trueName = Cookies.get("trueName");
        if (trueName){
            config.headers.trueName = trueName;
        }

        return config;
    },
    err => {
        return Promise.reject(err)
    }
)
instance.interceptors.response.use(
    response => {
        return response.data;
    },
    err => {
        //如果有具体错误，返回具体的"中文提示"
        console.log(err);
    }
)


export function get(url, params) {
    return instance.get(url, {params});
}

export function post(url, params) {
    return instance.post(url, params)
}

export function del(url, params) {
    return instance.delete(url, params)
}
