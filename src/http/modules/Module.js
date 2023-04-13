// eslint-disable-next-line no-unused-vars
import {get, post} from "@/http/axios";

export function queryAllModulesById(url,params) {
    return get(url,params)
}
export function queryModuleList(url,params) {
    return get(url,params)
}
export function delModuleById(url,params) {
    return post(url,params)
}
export function addModuleInfo(url,params) {
    return post(url,params)
}
export function queryModuleByGradeAndModuleName(url,params) {
    return post(url,params)
}
export function queryModuleByGradeAndUrl(url,params) {
    return post(url,params)
}
export function queryModuleByOptValue(url,params) {
    return post(url,params)
}
export function updateMenuInfo(url,params) {
    return post(url,params)
}