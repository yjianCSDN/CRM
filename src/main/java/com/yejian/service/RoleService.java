package com.yejian.service;

import com.yejian.bean.Role;
import com.yejian.bean.query.RoleQuery;

import java.util.List;
import java.util.Map;

/**
 * @Author
 * @Date
 */
public interface RoleService {
    List<Map<String,Object>> queryAllRoles(Integer userId);
    void addRole(Role role);
    void updateRole(Role role);
    void deleteRole(Integer roleId);
    void addGrant(Integer roleId, Integer[] mIds);
    Map<String, Object> queryByParamsForTable(RoleQuery roleQuery);
}
