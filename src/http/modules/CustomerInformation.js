// eslint-disable-next-line no-unused-vars
import {get, post} from "@/http/axios";

export function queryCustomerByParams(url,params) {
    return get(url,params)
}
export function deleteCustomer(url,params) {
    return post(url,params)
}
export function addCustomer(url,params) {
    return post(url,params)
}
export function updateCustomer(url,params) {
    return post(url,params)
}
export function queryCustomerOrderByParams(url,params) {
    return post(url,params)
}
export function scanOrderDetails(url,params) {
    return get(url,params)
}
export function queryOrderDetailsByParams(url,params) {
    return post(url,params)
}
export function queryCustomerLossByParams(url,params) {
    return get(url,params)
}
export function queryCustomerReprieveByParams(url,params) {
    return get(url,params)
}
export function addCustomerRepr(url,params) {
    return post(url,params)
}
export function updateCustomerRepr(url,params) {
    return post(url,params)
}
export function updateCustomerLossStateById(url,params) {
    return get(url,params)
}
export function queryLossCustomer(url,params) {
    return get(url,params)
}
export function addCustomerOrder(url,params) {
    return post(url,params)
}
export function addLossCustomerInfo(url,params) {
    return post(url,params)
}
export function setStateIsPay(url,params) {
    return get(url,params)
}
export function updateOrderInfo(url,params) {
    return post(url,params)
}