// eslint-disable-next-line no-unused-vars
import {get, post} from "@/http/axios";

export function queryByParams(url,params){
    return get(url,params)
}
export function delDictionary(url,params) {
    return post(url,params)
}
export function addDictionaryInfo(url,params) {
    return post(url,params)
}
export function updateDictionaryInfo(url,params) {
    return post(url,params)
}