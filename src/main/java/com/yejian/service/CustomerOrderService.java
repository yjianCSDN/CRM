package com.yejian.service;

import com.yejian.bean.CustomerOrder;
import com.yejian.bean.query.CustomerOrderQuery;

import java.util.Map;

/**
 * @Author
 * @Date
 */
public interface CustomerOrderService {
    Map<String, Object> queryCustomerOrderByParams(CustomerOrderQuery customerOrderQuery);
    Map<String, Object> queryOrderById(Integer orderId);
    Integer addOrders(CustomerOrder customerOrder);

    Integer setStateIsPay(Integer id);

    Integer updateCustomerInfo(CustomerOrder customerOrder);
}
