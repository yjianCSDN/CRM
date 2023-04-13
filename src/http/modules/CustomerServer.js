// eslint-disable-next-line no-unused-vars
import {get, post} from "@/http/axios";

export function queryCustomerServeByParams(url,params) {
    return get(url,params)
}
export function addCustomerServeInfo(url,params) {
    return post(url,params)
}
export function getInfoByName(url,params) {
    return get(url,params)
}
export function queryAllCustomerManagers(url,params) {
    return get(url,params)
}
export function updateCustomerServe(url,params) {
    return post(url,params)
}
export function queryCustomerContributionByParams(url,params) {
    return post(url,params)
}
export function countCustomerMake(url,params) {
    return get(url,params)
}
export function queryCustomerServeOrder(url,params) {
    return get(url,params)
}
export function addOrderDetails(url,params) {
    return post(url,params)
}
export function updateOrderDetails(url,params) {
    return post(url,params)
}
export function deleteOrderDetailsInfo(url,params) {
    return post(url,params)
}
