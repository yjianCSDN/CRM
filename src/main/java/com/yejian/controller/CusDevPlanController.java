package com.yejian.controller;

import com.yejian.bean.CusDevPlan;
import com.yejian.bean.query.CusDevPlanQuery;
import com.yejian.service.CusDevPlanService;
import com.yejian.utils.AssertUtil;
import com.yejian.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author
 * @Date
 */
@RestController
@RequestMapping("CusDevPlan")
public class CusDevPlanController {

    @Autowired
    private CusDevPlanService cusDevPlanService;


    /**
     * 查询总数
     * @param cusDevPlanQuery
     * @return com.yejian.utils.ResultInfo
     */
    @GetMapping("Total")
    public ResultInfo getTotal(CusDevPlanQuery cusDevPlanQuery){
        Integer total = cusDevPlanService.getTotal(cusDevPlanQuery);
        System.out.println("total:         "+total);
        if (null==total){
            return ResultInfo.error("查询total错误！");
        }else {
            return ResultInfo.ok(total);
        }
    }

    /***
     * 客户开发计划数据查询（分页多条件查询）
     * @param cusDevPlanQuery
     * @return com.yejian.utils.ResultInfo
     */
    @GetMapping("lists")
    public ResultInfo queryCusDevPlanByParams(CusDevPlanQuery cusDevPlanQuery){
        System.out.println(""+cusDevPlanQuery);
        Map<String, Object> map = cusDevPlanService.queryCusDevPlanByParams(cusDevPlanQuery);
        AssertUtil.isTrue(null==map,"查询失败，请重试！");
        return ResultInfo.ok(map);
    }


    /**
     * 添加计划项
     * @param addCusDevPlan
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("add")
    public ResultInfo addCusDevPlan(@RequestBody CusDevPlan addCusDevPlan) {
        System.out.println("addCusDevPlan:         "+addCusDevPlan);
        System.out.println(addCusDevPlan.getPlanDate());
        System.out.println(addCusDevPlan.getPlanItem());
        System.out.println(addCusDevPlan.getExeAffect());
        System.out.println(addCusDevPlan.getSaleChanceId());
        cusDevPlanService.addCusDevPlan(addCusDevPlan);
        return ResultInfo.ok("计划项添加成功！");
    }

    /**
     * 更新计划项
     * @param updateCusDevPlan
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("update")
    public ResultInfo updateCusDevPlan(@RequestBody CusDevPlan updateCusDevPlan) {
        System.out.println(updateCusDevPlan);
        System.out.println(updateCusDevPlan.getPlanDate());
        System.out.println(updateCusDevPlan.getPlanItem());
        System.out.println(updateCusDevPlan.getExeAffect());
        System.out.println(updateCusDevPlan.getSaleChanceId());
        cusDevPlanService.updateCusDevPlan(updateCusDevPlan);
        return ResultInfo.ok("计划项更新成功！");
    }

    /**
     * 删除计划项
     * @param id
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("delete")
    public ResultInfo deleteCusDevPlan(@RequestBody Integer id) {
        cusDevPlanService.deleteCusDevPlan(id);
        System.out.println(id);
        return ResultInfo.ok("计划项更新成功！");
    }
}
