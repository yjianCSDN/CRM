package com.yejian.service;

import com.yejian.bean.CustomerReprieve;
import com.yejian.bean.query.CustomerReprieveQuery;

import java.util.Map;

/**
 * @Author
 * @Date
 */
public interface CustomerReprieveService {
    Map<String, Object> queryCustomerReprieveByParams(CustomerReprieveQuery customerReprieveQuery);
    Integer addCustomerRepr(CustomerReprieve customerReprieve);
    Integer updateCustomerRepr(CustomerReprieve customerReprieve);
    Integer deleteCustomerRepr(Integer id);
}
