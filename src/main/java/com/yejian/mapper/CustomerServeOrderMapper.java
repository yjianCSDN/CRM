package com.yejian.mapper;

import com.yejian.bean.CustomerServeOrder;
import com.yejian.utils.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author
 * @Date
 */
@Mapper
public interface CustomerServeOrderMapper extends BaseMapper<CustomerServeOrder,Integer> {

    List<CustomerServeOrder> selectParams();

    Integer addOrder(CustomerServeOrder customerServeOrder);
}
