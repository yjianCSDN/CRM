package com.yejian.service;

import com.yejian.bean.User;
import com.yejian.bean.model.PwdModel;
import com.yejian.bean.model.UserModel;
import com.yejian.bean.query.UserQuery;

import java.util.List;
import java.util.Map;

/**
 * @Author
 * @Date
 */

public interface UserService {

     //登录
     UserModel login(String userName, String userPwd);
     //修改密码
     void updatePassWord(Integer id, String oldPwd, String newPwd);
     //查询销售
     List<Map<String,Object>> queryAllSales();
     //查询客户经理
     List<Map<String, Object>> queryAllCustomerManagers();
     //查询用户信息(条件查询)
     Map<String, Object> queryByParamsForTable(UserQuery userQuery);
     //添加用户信息
     void addUser(User user);
     //更新用户信息
     void updateUser(User user);
     //删除用户信息
     void deleteByIds(Integer id);

     boolean confirmUserPwd(PwdModel pwdModel);

     User getUserByUserName(String name);

     User queryUserByUserName(String userName);

     User queryUserById(Integer id);
}
