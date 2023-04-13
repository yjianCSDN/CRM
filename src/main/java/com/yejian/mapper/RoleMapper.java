package com.yejian.mapper;

import com.yejian.bean.Role;
import com.yejian.utils.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author
 * @Date
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role, Integer> {

    // 查询所有的角色列表 (只需要id与roleName)
    public List<Map<String,Object>> queryAllRoles(Integer userId);

    // 通过角色名查询角色记录
    public Role selectByRoleName(String roleName);
}
