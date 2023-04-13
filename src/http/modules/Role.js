// eslint-disable-next-line no-unused-vars
import {get, post} from "@/http/axios";

export function queryAllRoles(url,params) {
    return get(url,params)
}
export function selectByParams(url,params) {
    return get(url,params)
}
export function addRoleInfo(url,params) {
    return post(url,params)
}
export function deleteRole(url,params) {
    return post(url,params)
}
export function UpdateRole(url,params) {
    return post(url,params)
}
export function getEmpowerList(url,params) {
    return get(url,params)
}
export function addGrant(url,params) {
    return post(url,params)
}