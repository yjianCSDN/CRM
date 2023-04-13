package com.yejian.controller;

import com.yejian.bean.User;
import com.yejian.bean.model.PwdModel;
import com.yejian.bean.model.UserModel;
import com.yejian.bean.query.UserQuery;
import com.yejian.service.UserService;
import com.yejian.utils.BaseController;
import com.yejian.utils.Md5Util;
import com.yejian.utils.ResultInfo;
import com.yejian.utils.UserIDBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author
 * @Date
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param user  用户信息
     * @return
     */
    @PostMapping("login")
    public ResultInfo login(@RequestBody User user){
        ResultInfo info = new ResultInfo();
        UserModel model = userService.login(user.getUserName(),user.getUserPwd());
            info.setResult(model);
            info.setCode(200);
        return info;
    }

    /**
     * 修改用户密码
     * @param pwdModel
     * @return
     */
    @PostMapping("updatePassword")
    public ResultInfo updatePassWord( @RequestBody PwdModel pwdModel){
        ResultInfo info = new ResultInfo();
        Integer id = UserIDBase64.decoderUserID(pwdModel.getUserIdStr());
        userService.updatePassWord(id,pwdModel.getOldPwd(),pwdModel.getNewPwd());
        return info;
    }

    /**
     * 查询所有的销售人员
     * @param
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @GetMapping("queryAllSales")
    public ResultInfo queryAllSales(){
        List<Map<String, Object>> maps = userService.queryAllSales();
        return ResultInfo.ok(maps);
    }



    /**
     * 分页多条件查询用户列表
     * @param userQuery
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @PostMapping("lists")
    public ResultInfo selectByParams(@RequestBody UserQuery userQuery) {
        System.out.println(userQuery);
        System.out.println("userName: "+userQuery.getUserName());
        System.out.println("email: "+userQuery.getEmail());
        System.out.println("phone: "+userQuery.getPhone());
        Map<String, Object> map = userService.queryByParamsForTable(userQuery);
        return ResultInfo.ok(map);
    }




    /**
     * 查询所有的客户经理
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @GetMapping("queryAllCustomerManagers")
    public ResultInfo queryAllCustomerManagers(){
        List<Map<String, Object>> maps = userService.queryAllCustomerManagers();
        return ResultInfo.ok(maps);
    }


    /**
     * 添加用户
     * @param user
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("addUser")
    public ResultInfo addUser(@RequestBody User user){
//        System.out.println(user.getTrueName());
//        System.out.println(user.getUserName());
//        System.out.println(user.getEmail());
//        System.out.println(user.getPhone());
//        System.out.println(user.getUserPwd());
//        System.out.println("RoleIds:   "+user.getRoleIds());
        userService.addUser(user);
        return ResultInfo.ok("添加成功!");
    }

    /**
     * 更新用户
     * @param user
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("updateUser")
    public ResultInfo updateUser(@RequestBody User user,  HttpServletRequest request){
        String name = request.getHeader("userName");
        User user1 = userService.queryUserByUserName(name);
//        System.out.println("flag:---------------------------------------->"+flag);
//        System.out.println(user.getTrueName());
//        System.out.println(user.getUserName());
//        System.out.println(user.getEmail());
//        System.out.println(user.getPhone());
//        System.out.println(user.getUserPwd());
//        System.out.println(user.getRoleIds());
//        System.out.println(user.getSex());
        if (user1.getUserPwd().equals(user.getUserPwd())){
//            System.out.println("用户没有修改密码");
        }else {
//            System.out.println("用户更新了密码");
            String encode = Md5Util.encode(user.getUserPwd());
            user.setUserPwd(encode);
        }
        /**
         * 判断是否为个人中心修改资料，如果是，需要将权限码取出然后赋值
         */
//        if (flag==1){
//            System.out.println("用户是从个人中心进行信息修改的");
//            User user2 = userService.queryUserById(user.getId());
//            System.out.println("asdasdasdasdasdasd"+user2.getRoleIds());
//            user.setRoleIds(user2.getRoleIds());
//        }
        userService.updateUser(user);
        return ResultInfo.ok("用户更新成功!");
    }


    /**
     * 删除用户
     * @param id
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("deleteUser")
    public ResultInfo deleteUser(@RequestBody Integer id){
//        System.out.println("del Id:  "+id);
        userService.deleteByIds(id);
        return ResultInfo.ok();
    }


    /**
     * 确认密码
     * @param pwdModel
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("confirmPwd")
    public ResultInfo confirmUserPwd(@RequestBody PwdModel pwdModel){
//        System.out.println(pwdModel.getUserIdStr());
//        System.out.println(Integer.valueOf(pwdModel.getUserIdStr()));
//        System.out.println(pwdModel.getOldPwd());
        boolean b = userService.confirmUserPwd(pwdModel);
        if (b){
            return ResultInfo.ok("正确!");
        }else {
            return ResultInfo.error("错误!");
        }
    }


    /**
     * 查看个人资料
     * @param userName
     * @return com.yejian.utils.ResultInfo
     */
    @GetMapping("getUserByUserName")
    public ResultInfo getUserByUserName(String userName){
        User user = userService.getUserByUserName(userName);
        System.out.println(user.getRoleIds());
        if (user.equals(null)) {
            return ResultInfo.error("error!");
        }else {
            return ResultInfo.ok(user,"ok!");
        }
    }

}
