// eslint-disable-next-line no-unused-vars
import {get, post} from "@/http/axios";

export function updatePassword(url,params){
    return post(url,params)
}
export function queryAllSales(url,params){
    return get(url,params)
}
export function addSaleChance(url,params){
    return post(url,params)
}
export function selectByParams(url,params) {
    return post(url,params)
}
export function selectList(url,params){
    return post(url,params)
}
export function addUser(url,params){
    return post(url,params)
}
export function confirmPwd(url,params){
    return post(url,params)
}
export function updateUser(url,params) {
    return post(url,params)
}
export function deleteUser(url,params) {
    return post(url,params)
}
export function queryAllRoles(url,params) {
    return get(url,params)
}
export function queryUserByName(url,params) {
    return get(url,params)
}