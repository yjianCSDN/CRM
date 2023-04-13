package com.yejian.service;

import com.yejian.bean.CustomerServeOrder;
import com.yejian.bean.query.CustomerQuery;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author
 * @Date
 */
@Service
public interface CustomerServeOrderService {

    Map<String, Object> selectParams(CustomerQuery customerQuery);

    Integer addCustomerList(CustomerServeOrder customerServeOrder);
}
