package com.yejian.mapper;

import com.yejian.bean.CustomerServe;
import com.yejian.utils.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author
 * @Date
 */
@Mapper
public interface CustomerServeMapper extends BaseMapper<CustomerServe, Integer> {

    List<Map<String,Object>> countCustomerMake();
}
