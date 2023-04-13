package com.yejian.service;

import com.yejian.bean.CusDevPlan;
import com.yejian.bean.query.CusDevPlanQuery;

import java.util.Map;

/**
 * @Author
 * @Date
 */
public interface CusDevPlanService {

    Map<String, Object> queryCusDevPlanByParams(CusDevPlanQuery cusDevPlanQuery);

    Integer getTotal(CusDevPlanQuery cusDevPlanQuery);

    int deleteCusDevPlan(Integer id);

    void addCusDevPlan(CusDevPlan cusDevPlan);

    void updateCusDevPlan(CusDevPlan cusDevPlan);
}
