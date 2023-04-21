import {get, post} from "@/http/axios";

export function queryNotice(url,params){
    return get(url,params)
}

export function updateNotice(url,params){
    return post(url,params)
}

export function getTotal(url,params){
    return get(url,params)
}

export function delNotice(url,params){
    return post(url,params)
}

export function addNotice(url,params) {
    return post(url,params)
}

export function updateCDP(url,params) {
    return post(url,params)
}