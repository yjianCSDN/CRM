import {get, post} from "@/http/axios";

export function Login(url,params){
    return post(url,params)
}
export function getAllName(url,params){
    return get(url,params)
}

export function register(url,params){
    return post(url,params)
}
export function getRoleByName(url,params){
    return get(url,params)
}
export function updateRole(url,params){
    return post(url,params)
}