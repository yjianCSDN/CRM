package com.yejian.mapper;

import com.yejian.bean.CustomerOrder;
import com.yejian.utils.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @Author
 * @Date
 */
@Mapper
public interface CustomerOrderMapper extends BaseMapper<CustomerOrder, Integer> {

    // 通过订单ID查询订单记录
    Map<String ,Object> queryOrderById(Integer orderId);

    // 通过客户ID查询最后一条订单记录
    CustomerOrder queryLossCustomerOrderByCustomerId(Integer customerId);



}
