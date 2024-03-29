package com.yejian.mapper;

import com.yejian.bean.CusDevPlan;
import com.yejian.bean.query.CusDevPlanQuery;
import com.yejian.utils.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author
 * @Date
 */
@Mapper
public interface CusDevPlanMapper extends BaseMapper<CusDevPlan,Integer> {

    Integer getTotal(CusDevPlanQuery cusDevPlanQuery);

    List<Object> getItems(String name);
}
