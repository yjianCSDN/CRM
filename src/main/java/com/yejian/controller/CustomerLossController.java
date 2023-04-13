package com.yejian.controller;

import com.yejian.bean.CustomerLoss;
import com.yejian.bean.query.CustomerLossQuery;
import com.yejian.service.CustomerLossService;
import com.yejian.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author
 * @Date
 */
@RestController
@RequestMapping("customerLoss")
public class CustomerLossController {

    @Autowired
    private CustomerLossService customerLossService;

    /**
     * 分页条件查询流失客户列表
     *
     * @param customerLossQuery
     * @return com.yejian.utils.ResultInfo
     */
    @GetMapping("list")
    public ResultInfo queryCustomerLossByParams(CustomerLossQuery customerLossQuery) {
        Map<String, Object> map = customerLossService.queryCustomerLossByParams(customerLossQuery);
        if (null==map){
            return ResultInfo.error("查询失败");
        }else {
            return ResultInfo.ok(map,"查询成功!");
        }
    }

    /**
     * 更新流失客户的流失状态
     *
     * @param id,lossReason
     * @return com.yejian.utils.ResultInfo
     */
    @GetMapping("updateCustomerLossStateById")
    public ResultInfo updateCustomerLossStateById(Integer id, String lossReason) {
        System.out.println(id);
        System.out.println(lossReason);
        Integer id1 = customerLossService.updateCustomerLossStateById(id, lossReason);
        if (id1<1){
            return ResultInfo.error("更新失败，请重试");
        }
        return ResultInfo.ok("确认流失成功！");
    }


    @PostMapping("addLossInfo")
    public ResultInfo addLossInfo(@RequestBody CustomerLoss customerLoss){
        System.out.println(customerLoss.getLastOrderTime());
        System.out.println(customerLoss.getCusName());
        Integer integer = customerLossService.insertLossInfo(customerLoss);
        if (integer<1){
            return ResultInfo.error("添加失败");
        }else {
            return ResultInfo.ok("添加成功");
        }
    }
}
