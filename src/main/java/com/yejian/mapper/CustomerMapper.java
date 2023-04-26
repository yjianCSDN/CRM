package com.yejian.mapper;

import com.yejian.bean.Customer;
import com.yejian.bean.query.CustomerQuery;
import com.yejian.utils.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author yejian
 * @Date
 */
@Mapper
public interface CustomerMapper extends BaseMapper<Customer,Integer> {

    // 通过客户名称查询客户对象
    Customer queryCustomerByName(String name);

    // 查询待流失的客户数据
    List<Customer> queryLossCustomers();

    // 通过客户ID批量更新客户流失状态
    int updateCustomerStateByIds(List<Integer> lossCustomerIds);

    // 查询客户贡献数据
    List<Map<String,Object>> queryCustomerContributionByParams(CustomerQuery customerQuery);

    // 查询客户构成
    List<Map<String,Object>> countCustomerMake();

    List<Map<String,Object>> countOrderByCustomer(String userName);

    List<String> getCusPhones();


}
