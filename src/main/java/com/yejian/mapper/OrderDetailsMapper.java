package com.yejian.mapper;

import com.yejian.bean.OrderDetails;
import com.yejian.utils.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author
 * @Date
 */
@Mapper
public interface OrderDetailsMapper extends BaseMapper<OrderDetails,Integer> {
    Integer deleteInfo(Integer id);
}
