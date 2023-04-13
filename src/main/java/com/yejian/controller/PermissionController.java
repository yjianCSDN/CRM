package com.yejian.controller;

import com.yejian.service.PermissionService;
import com.yejian.utils.BaseController;
import com.yejian.utils.ResultInfo;
import com.yejian.utils.UserIDBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author
 * @Date
 */
@RestController
@RequestMapping("permission")
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("lists")
    public ResultInfo queryUserHasRoleHasPermissionByUserId(@RequestParam(value = "userIdStr") String userId){
        System.out.println("ID:        -------->"+userId);
        Integer id = UserIDBase64.decoderUserID(userId);
        System.out.println("解密后的id为:  --->" + id);
        List<String> list = permissionService.queryUserHasRoleHasPermissionByUserId(id);
//        System.out.println(list.iterator());
        if (null==list){
            return ResultInfo.error("查询失败!");
        }else {
            return ResultInfo.ok(list,"查询成功!");
        }
    }
}
