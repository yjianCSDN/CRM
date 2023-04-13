package com.yejian.controller;

import com.yejian.bean.Role;
import com.yejian.bean.model.addGrantModel;
import com.yejian.bean.query.RoleQuery;
import com.yejian.service.RoleService;
import com.yejian.utils.AssertUtil;
import com.yejian.utils.BaseController;
import com.yejian.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author
 * @Date
 */
@RestController
@RequestMapping("role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 根据uid来查询角色信息
     * @param userId
     * @return com.yejian.utils.ResultInfo
     */
    @GetMapping("queryAllRoles")
    public ResultInfo queryAllRoles(Integer userId){
        List<Map<String, Object>> maps = roleService.queryAllRoles(userId);
        if (null==maps){
            return ResultInfo.error("查询出错!");
        }else {
            return ResultInfo.ok(maps,"查询成功");
        }
    }


    /**
     * 条件查询角色列表
     * @param roleQuery
     * @return com.yejian.utils.ResultInfo
     */
    @GetMapping("lists")
    public ResultInfo selectByParams(RoleQuery roleQuery){
        Map<String, Object> map = roleService.queryByParamsForTable(roleQuery);
        AssertUtil.isTrue(map==null,"查询失败");
        return ResultInfo.ok(map);
    }

    /**
     * 添加角色
     * @param role
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("addRole")
    public ResultInfo addRole(@RequestBody Role role){
        roleService.addRole(role);
        return ResultInfo.ok("添加成功!");
    }

    /**
     * 更新角色信息
     * @param role
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("updateRole")
    public ResultInfo updateRole(@RequestBody Role role){
        System.out.println(role.getRoleName());
        System.out.println(role.getRoleRemark());
        roleService.updateRole(role);
        return ResultInfo.ok("更新成功!");
    }

    /**
     * 删除角色
     * @param id
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("deleteRole")
    public ResultInfo deleteRole(@RequestBody Integer id){
//        System.out.println("id:     "+id);
        roleService.deleteRole(id);
        return ResultInfo.ok("删除成功!");
    }

    /**
     * 角色授权
     * @param addGrantModel
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("addGrant")
    public ResultInfo addGrant(@RequestBody addGrantModel addGrantModel) {

        Integer roleId = addGrantModel.getRoleId();
        Integer[] mIds = addGrantModel.getMIds();
        for (int i = 0; i < mIds.length; i++) {
            System.out.println(mIds[i]);
        }
        System.out.println("roleId: "+roleId);
        System.out.println("mIds:"+mIds);
        roleService.addGrant(roleId, mIds);

        return success("角色授权成功！");
    }
}
