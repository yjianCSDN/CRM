package com.yejian.service;

import com.yejian.bean.Customer;
import com.yejian.bean.query.CustomerQuery;

import java.util.Map;

/**
 * @Author
 * @Date
 */
public interface CustomerService {

    Map<String, Object> queryCustomerByParams(CustomerQuery customerQuery);
    Integer addCustomer(Customer customer);
    Integer updateCustomer(Customer customer);
    Integer deleteCustomer(Integer id);
    void updateCustomerState();
    Map<String,Object> queryCustomerContributionByParams(CustomerQuery customerQuery);
    Map<String, Object> countCustomerMake();
    Map<String, Object> countCustomerMake02();
}
