package com.yejian.service;

import com.yejian.bean.Customer;
import com.yejian.bean.CustomerServe;
import com.yejian.bean.query.CustomerServeQuery;

import java.util.Map;

/**
 * @Author
 * @Date
 */
public interface CustomerServeService {

    Customer getInfoByName(String customer);
    Map<String, Object> queryCustomerServeByParams(CustomerServeQuery customerServeQuery);
    Integer addCustomerServe(CustomerServe customerServe);
    Integer updateCustomerServe(CustomerServe customerServe);

    Map<String, Object> countCustomerMake02();

    Map<String, Object> countCustomerMake();
}
