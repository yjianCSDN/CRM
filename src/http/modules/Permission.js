// eslint-disable-next-line no-unused-vars
import {get, post} from "@/http/axios";

export function queryUserHasRoleHasPermissionByUserId(url,params) {
    return get(url,params)
}
