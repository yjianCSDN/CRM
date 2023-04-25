package com.yejian.controller;

import com.yejian.bean.SaleChance;
import com.yejian.bean.query.SaleChanceQuery;
import com.yejian.bean.query.DevResultQuery;
import com.yejian.enums.StateStatus;
import com.yejian.service.SaleChanceService;
import com.yejian.utils.BaseController;
import com.yejian.utils.CookieUtil;
import com.yejian.utils.LoginUserUtil;
import com.yejian.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author
 * @Date
 */
@RestController
@CrossOrigin    //@CrossOrigin允许在@RequestMapping注解中指定的所有源和HTTP方法
@RequestMapping("SaleChance")
public class SaleChanceController extends BaseController {

    @Autowired
    private SaleChanceService saleChanceService;

    @GetMapping("getAll")
    public ResultInfo getAll(){
        Integer num = saleChanceService.getAll();
        System.out.println("获取到的总数为： "+num);
        return  ResultInfo.ok(num,"获取成功");
    }


    /**
     * 营销机会查询（多条件查询）
     * @param saleChanceQuery
     * @return
     */
    @GetMapping("lists")
    public ResultInfo querySaleChanceByParams(SaleChanceQuery saleChanceQuery,Integer flag, HttpServletRequest request){
        System.out.println("headers :"+request.getHeader("userName"));
        System.out.println("flag:     "+flag);
        // 判断flag的值
        if (flag != null && flag == 1) {
            // 查询客户开发计划
            // 设置分配状态
            saleChanceQuery.setState(StateStatus.STATED.getType());
            // 设置指派人（当前登录用户的ID）
            // 从cookie中获取当前登录用户的ID
//            Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
            saleChanceQuery.setAssignMan(request.getHeader("userName"));
        }
    Map<String, Object> map = saleChanceService.querySaleChanceByParams(saleChanceQuery);
        if (map.isEmpty()){
            return ResultInfo.error("操作错误，请重试");
        }else {
            return ResultInfo.ok(map);
        }
    }

    /**
     * 添加营销机会
     * @param saleChance
     * @param request 从这获取userName
     * @return
     */
    @PostMapping("add")
    public ResultInfo saveSaleChance(@RequestBody SaleChance saleChance, HttpServletRequest request){
        String name = request.getHeader("userName");
        String userName = CookieUtil.getCookieValue(request, "userName");
        System.out.println("userName为   " + userName);
        System.out.println("request获得到的userName为 " + name);
        saleChance.setCreateMan(userName);
        saleChance.setId(null);
        int i = LoginUserUtil.releaseUserIdFromCookie(request);
        saleChanceService.addSaleChance(saleChance);
        System.out.println("addSaleChance:    "+saleChance);
        return ResultInfo.ok();
    }

    /**
     * 更新营销机会
     * @param saleChance
     * @return
     */
    @PostMapping("/updateSaleChance")
    public ResultInfo updateSaleChance(@RequestBody SaleChance saleChance) {
        saleChanceService.updateSaleChance(saleChance);
        return ResultInfo.ok("更新数据成功！");
    }


    /**
     * 删除营销机会 101003
     *
     * @param id
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("delete")
    public ResultInfo deleteSaleChance(@RequestBody Integer id) {
        System.out.println(id);
        saleChanceService.deleteSaleChance(id);
        return ResultInfo.ok("删除成功！");
    }

    /**
     * 更新营销机会的开发状态
     * @param devResult
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("updateSaleChanceDevResult")
    public ResultInfo updateSaleChanceDevResult(@RequestBody DevResultQuery devResult) {
        Integer ids = devResult.getId();
        Integer devResults = devResult.getDevResult();
        System.out.println("ids:   "+ids);
        System.out.println("devResults:     "+devResults);
//        System.out.println("id:   "+id);
        System.out.println("devResult:     "+devResult);
        saleChanceService.updateSaleChanceDevResult(ids, devResults);

        return success("开发状态更新成功！");
    }

}
