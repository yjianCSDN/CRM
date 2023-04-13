import {get, post} from "@/http/axios";

export function queryCusDevPlanByParams(url,params){
    return get(url,params)
}

export function updateRole(url,params){
    return post(url,params)
}

export function getTotal(url,params){
    return get(url,params)
}

export function delCusDevPlan(url,params){
    return post(url,params)
}

export function addCusDevPlan(url,params) {
    return post(url,params)
}

export function updateCDP(url,params) {
    return post(url,params)
}