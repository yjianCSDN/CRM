import {get,post} from "@/http/axios";

export function findAllNews(url,params){
    return get(url,params)
}
export function submit(url,params){
    return post(url,params)
}
export function deleteById(url,params){
    return post(url,params)
}

export function updateNews(url,params){
    return post(url,params)
}
export function SelFind(url,params){
    return get(url,params)
}