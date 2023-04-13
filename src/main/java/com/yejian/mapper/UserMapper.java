package com.yejian.mapper;

import com.yejian.bean.User;
import com.yejian.utils.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author
 * @Date
 */
@Mapper
public interface UserMapper extends BaseMapper<User,Integer> {
    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
     User queryUserByName(String userName);

     @Select("select * from t_user")
     List<User> aaa();

    /**
     * 查询所有销售人员
     */
    List<Map<String, Object>> queryAllSales();

    /**
     * 查询所有客户经理
     */
    List<Map<String, Object>> queryAllCustomerManagers();

    /**
     * 删除用户信息
     * @param id
     * @return Integer
     */
    int deleteByIds(Integer id);

}
