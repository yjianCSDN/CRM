package com.yejian.service;

import java.util.List;

/**
 * @Author
 * @Date
 */
public interface PermissionService {
//    通过查询用户拥有的角色，角色拥有的资源，得到用户拥有的资源列表 （资源权限码）
    List<String> queryUserHasRoleHasPermissionByUserId(Integer userId);
}
