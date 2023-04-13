package com.yejian.service;

import com.yejian.bean.OrderDetails;
import com.yejian.bean.query.OrderDetailsQuery;

import java.util.Map;

/**
 * @Author
 * @Date
 */
public interface OrderDetailsService {

    Map<String, Object> queryOrderDetailsByParams(OrderDetailsQuery orderDetailsQuery);

    Integer addOrderInfo(OrderDetails orderDetails);

    Integer updateOrderInfo(OrderDetails orderDetails);

    Integer deleteInfo(Integer id);
}
