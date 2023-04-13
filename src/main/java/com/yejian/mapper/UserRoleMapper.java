package com.yejian.mapper;

import com.yejian.bean.UserRole;
import com.yejian.utils.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author
 * @Date
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {


    // 根据用户ID查询用户角色记录
    Integer countUserRoleByUserId(Integer userId);

    // 根据用户ID删除用户角色记录
    Integer deleteUserRoleByUserId(Integer userId);
}
