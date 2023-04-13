package com.yejian.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yejian.bean.User;
import com.yejian.bean.UserRole;
import com.yejian.bean.model.PwdModel;
import com.yejian.bean.model.UserModel;
import com.yejian.bean.query.UserQuery;
import com.yejian.mapper.UserMapper;
import com.yejian.mapper.UserRoleMapper;
import com.yejian.service.UserService;
import com.yejian.utils.AssertUtil;
import com.yejian.utils.BaseService;
import com.yejian.utils.Md5Util;
import com.yejian.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author
 * @Date
 */
@Service
public class UserServiceImpl extends BaseService<User,Integer> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 用户登录
     * @param userName  用户名
     * @param userPwd   密码
     * @return
     */
    @Override
    public UserModel login(String userName, String userPwd) {
        System.out.println(userName + "  Impl  " + userPwd);
        User user = userMapper.queryUserByName(userName);
        System.out.println(user.getUserPwd());
        AssertUtil.isTrue(null==user,"用户不存在或注销");
        //密码不正确
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(userPwd)),"密码错误");
        UserModel userModel = buildModel(user);
        return userModel;
    }

    /**
     * 转换部分用户信息
     * id需要加密
     * @param user 要转换的信息
     * @return 部分信息
     */
    private UserModel buildModel(User user){
        UserModel model = new UserModel();
        model.setUserName(user.getUserName());
        model.setTrueName(user.getTrueName());
        model.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        return model;
    }

    /**
     * 更新用户密码
     * @param id
     * @param oldPwd
     * @param newPwd
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePassWord(Integer id, String oldPwd, String newPwd) {
        User user = userMapper.selectByPrimaryKey(id);
        System.out.println("impl的user为 "+user);
        System.out.println("user的密码为 "+user.getUserPwd());
        System.out.println("oldPwd密码加密为 "+Md5Util.encode(oldPwd));
        // 判断用户记录是否存在
        AssertUtil.isTrue(null == user, "待更新记录不存在！");

        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPwd)),"原密码不正确");
        // 参数校验(前端完成)

        // 设置用户的新密码
        user.setUserPwd(Md5Util.encode(newPwd));

        // 执行更新，判断受影响的行数
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1, "修改密码失败！");

    }

    /**
     * 查询所有销售人员
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    public List<Map<String,Object>> queryAllSales(){
        return userMapper.queryAllSales();
    }

    /**
     * 查询所有客户经理
     */
    public List<Map<String, Object>> queryAllCustomerManagers() {
        return userMapper.queryAllCustomerManagers();
    }

    /**
     * 查询数据表格对应的数据
     * @param userQuery
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> queryByParamsForTable(UserQuery userQuery) {
        HashMap<String, Object> hashMap = new HashMap<String,Object>();
        PageHelper.startPage(userQuery.getPage(),userQuery.getLimit());
//        System.out.println(userQuery.getUserName());
        PageInfo<User> pageInfo = new PageInfo<User>(userMapper.selectByParams(userQuery));
//        System.out.println(pageInfo.getList());
        AssertUtil.isTrue(null==pageInfo,"查询错误！");
        hashMap.put("code",200);
        hashMap.put("msg","查询成功");
        hashMap.put("count",pageInfo.getTotal());
        hashMap.put("data",pageInfo.getList());
        return hashMap;
    }


    /**
     * 添加用户
     *  1. 参数校验(前端实现)
     *      用户名userName     非空，唯一性
     *      邮箱email          非空
     *      手机号phone        非空，格式正确
     *  2. 设置参数的默认值
     *      isValid           1
     *      createDate        系统当前时间
     *      updateDate        系统当前时间
     *      默认密码            123456 -> md5加密
     *  3. 执行添加操作，判断受影响的行数
     * @param user
     * @return void
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(User user) {
        /* 1. 参数校验 */
        // 判断用户名的唯一性
        // 通过用户名查询用户对象
        User temp = userMapper.queryUserByName(user.getUserName());
        // 如果用户对象为空，则表示用户名可用；如果用户对象不为空，则表示用户名不可用
        // 如果是添加操作，数据库中无数据，只要通过名称查到数据，则表示用户名被占用
        // 如果是修改操作，数据库中有对应的记录，通过用户名查到数据，可能是当前记录本身，也可能是别的记录
        // 如果用户名存在，且与当前修改记录不是同一个，则表示其他记录占用了该用户名，不可用
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(user.getId())), "用户名已存在，请重新输入！");

        /* 2. 设置参数的默认值 */
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        // 设置默认密码
//        user.setUserPwd(Md5Util.encode("123456"));
        user.setUserPwd(Md5Util.encode(user.getUserPwd()));
        /* 3. 执行添加操作，判断受影响的行数 */
        AssertUtil.isTrue(userMapper.insertSelective(user) < 1, "用户添加失败！");

        /* 用户角色关联 */
        /**
         * 用户ID
         *  userId
         * 角色ID
         *  roleIds
         */
        relationUserRole(user.getId(), user.getRoleIds());

    }


    /**
     * 用户角色关联
     *  添加操作
     *      原始角色不存在
     *          1. 不添加新的角色记录    不操作用户角色表
     *          2. 添加新的角色记录      给指定用户绑定相关的角色记录
     *
     *  更新操作
     *      原始角色不存在
     *          1. 不添加新的角色记录     不操作用户角色表
     *          2. 添加新的角色记录       给指定用户绑定相关的角色记录
     *      原始角色存在
     *          1. 添加新的角色记录       判断已有的角色记录不添加，添加没有的角色记录
     *          2. 清空所有的角色记录     删除用户绑定角色记录
     *          3. 移除部分角色记录       删除不存在的角色记录，存在的角色记录保留
     *          4. 移除部分角色，添加新的角色    删除不存在的角色记录，存在的角色记录保留，添加新的角色
     *
     *   如何进行角色分配？？？
     *      判断用户对应的角色记录存在，先将用户原有的角色记录删除，再添加新的角色记录
     *
     *  删除操作
     *      删除指定用户绑定的角色记录
     * @param userId  用户ID
     * @param roleIds 角色ID
     * @return
     */
    private void relationUserRole(Integer userId, String roleIds) {

        // 通过用户ID查询角色记录
        Integer count = userRoleMapper.countUserRoleByUserId(userId);
        // 判断角色记录是否存在
        if (count > 0) {
            // 如果角色记录存在，则删除该用户对应的角色记录
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId) != count, "用户角色分配失败！");
        }

        // 判断角色ID是否存在，如果存在，则添加该用户对应的角色记录
        if (StringUtils.isNotBlank(roleIds)) {
            // 将用户角色数据设置到集合中，执行批量添加
            List<UserRole> userRoleList = new ArrayList<>();
            // 将角色ID字符串转换成数组
            String[] roleIdsArray = roleIds.split(",");
            // 遍历数组，得到对应的用户角色对象，并设置到集合中
            for (String roleId : roleIdsArray) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(Integer.parseInt(roleId));
                userRole.setUserId(userId);
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                // 设置到集合中
                userRoleList.add(userRole);
            }
            // 批量添加用户角色记录
            AssertUtil.isTrue(userRoleMapper.insertBatch(userRoleList) != userRoleList.size(), "用户角色分配失败！");
        }
    }



    /**
     * 更新用户
     *  1. 参数校验
     *      判断用户ID是否为空，且数据存在
     *      用户名userName     非空，唯一性
     *      邮箱email          非空
     *      手机号phone        非空，格式正确
     *  2. 设置参数的默认值
     *      updateDate        系统当前时间
     *  3. 执行更新操作，判断受影响的行数
     * @param user
     * @return void
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user) {
        // 判断用户ID是否为空，且数据存在
        AssertUtil.isTrue(null == user.getId(), "待更新记录不存在！");
        // 通过id查询数据
        User temp = userMapper.selectByPrimaryKey(user.getId());
        // 判断是否存在
        AssertUtil.isTrue(null == temp, "待更新记录不存在！");

        // 判断用户名的唯一性
        // 通过用户名查询用户对象
        User temp1 = userMapper.queryUserByName(user.getUserName());
        // 如果用户对象为空，则表示用户名可用；如果用户对象不为空，则表示用户名不可用
        // 如果是添加操作，数据库中无数据，只要通过名称查到数据，则表示用户名被占用
        // 如果是修改操作，数据库中有对应的记录，通过用户名查到数据，可能是当前记录本身，也可能是别的记录
        // 如果用户名存在，且与当前修改记录不是同一个，则表示其他记录占用了该用户名，不可用
        AssertUtil.isTrue(null != temp1 && !(temp.getId().equals(user.getId())), "用户名已存在，请重新输入！");


        // 设置默认值
        user.setUpdateDate(new Date());

        // 执行更新操作，判断受影响的行数
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) != 1, "用户更新失败！");


        /* 用户角色关联 */
        /**
         * 用户ID
         *  userId
         * 角色ID
         *  roleIds
         */
        relationUserRole(user.getId(), user.getRoleIds());


    }

    /**
     * 删除用户信息
     * @param id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIds(Integer id) {
        // 判断ids是否为空，长度是否大于0
        AssertUtil.isTrue(id == null, "待删除记录不存在！");
        //执行操作
        int i = userMapper.deleteByIds(id);
        AssertUtil.isTrue(i<0,"用户删除失败");
        // 通过用户ID查询对应的用户角色记录
        Integer count = userRoleMapper.countUserRoleByUserId(id);
        if (count>0){
            //  通过用户ID删除对应的用户角色记录
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(id) != count, "删除用户失败");
        }
    }

    @Override
    public boolean confirmUserPwd(PwdModel pwdModel) {
//      通过userId来查找出该用户密码是否正确
        User temp = userMapper.selectByPrimaryKey(Integer.valueOf(pwdModel.getUserIdStr()));
        return temp.getUserPwd().equals(Md5Util.encode(pwdModel.getOldPwd()));
    }

    @Override
    public User getUserByUserName(String userName) {
        User user = userMapper.queryUserByName(userName);
        return user;
    }

    @Override
    public User queryUserByUserName(String userName){
        return userMapper.queryUserByName(userName);
    }

    @Override
    public User queryUserById(Integer id){
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

}
