// eslint-disable-next-line no-unused-vars
import {get, post} from "@/http/axios";

export function querySaleChanceByParams(url,params){
    return get(url,params)
}

export function addSaleChance(url,params){
    return post(url,params)
}

export function updateSaleChance(url,params) {
    return post(url,params)
}

export function delSaleChance(url,params) {
    return post(url,params)
}

export function getAll(url,params) {
    return get(url,params)
}

export function updateSaleChanceDevResult(url,params) {
    return post(url,params)
}