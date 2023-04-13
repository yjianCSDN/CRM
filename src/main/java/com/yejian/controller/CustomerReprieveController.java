package com.yejian.controller;

import com.yejian.bean.CustomerReprieve;
import com.yejian.bean.query.CustomerReprieveQuery;
import com.yejian.service.CustomerReprieveService;
import com.yejian.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author
 * @Date
 */
@RestController
@RequestMapping("customerReprieve")
public class CustomerReprieveController {

    @Autowired
    private CustomerReprieveService customerReprieveService;

    /**
     * 分页条件查询流失客户暂缓操作的列表
     *
     * @param customerReprieveQuery
     * @return com.yejian.utils.ResultInfo
     */
    @GetMapping("lists")
    public ResultInfo queryCustomerReprieveByParams(CustomerReprieveQuery customerReprieveQuery) {
        Map<String, Object> map = customerReprieveService.queryCustomerReprieveByParams(customerReprieveQuery);
        if (null==map) {
            return ResultInfo.error("查询失败");
        }else {
            return ResultInfo.ok(map,"成功!");
        }
    }

    /**
     * 添加暂缓数据
     *
     * @param customerReprieve
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("add")
    public ResultInfo addCustomerRepr(@RequestBody CustomerReprieve customerReprieve){
        Integer integer = customerReprieveService.addCustomerRepr(customerReprieve);
        if (integer<1){
            return ResultInfo.error("添加失败");
        }else {
            return ResultInfo.ok("添加成功");
        }
    }

    /**
     * 修改暂缓数据
     *
     * @param customerReprieve
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("update")
    public ResultInfo updateCustomerRepr(@RequestBody CustomerReprieve customerReprieve){
        Integer integer = customerReprieveService.updateCustomerRepr(customerReprieve);
        if (integer<1){
            return ResultInfo.error("修改失败");
        }else {
            return ResultInfo.ok("修改成功");
        }
    }

    /**
     * 删除暂缓数据
     *
     * @param id
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("delete")
    public ResultInfo updateCustomerRepr(@RequestBody Integer id){
        Integer integer = customerReprieveService.deleteCustomerRepr(id);
        if (integer<1){
            return ResultInfo.error("删除失败");
        }else {
            return ResultInfo.ok("删除成功");
        }
    }
}
