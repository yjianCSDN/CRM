package com.yejian.service.Impl;

import com.yejian.bean.Permission;
import com.yejian.mapper.PermissionMapper;
import com.yejian.service.PermissionService;
import com.yejian.utils.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author
 * @Date
 */
@Service
public class PermissionServiceImpl extends BaseService<Permission,Integer> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    /***
     * 通过查询用户拥有的角色，角色拥有的资源，得到用户拥有的资源列表 （资源权限码）
     *
     * @param userId
     * @return java.util.List<java.lang.String>
     */
    @Override
    public List<String> queryUserHasRoleHasPermissionByUserId(Integer userId) {
        List<String> list = permissionMapper.queryUserHasRoleHasPermissionByUserId(userId);
        return list;
    }
}
