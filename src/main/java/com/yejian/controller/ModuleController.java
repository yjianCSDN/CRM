package com.yejian.controller;

import com.yejian.bean.Module;
import com.yejian.bean.model.TreeModel;
import com.yejian.bean.query.ModuleQuery;
import com.yejian.service.ModuleService;
import com.yejian.utils.BaseController;
import com.yejian.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author
 * @Date
 */
@RestController
@RequestMapping("module")
public class ModuleController extends BaseController {
    @Autowired
    private ModuleService moduleService;


    /**
     * 根据角色id查询所有的资源列表
     *
     * @param
     * @return com.yejian.utils.ResultInfo
     */
    @GetMapping("queryAllModules")
    public ResultInfo queryAllModules(@RequestParam Integer roleId){
//        System.out.println("2333roleId:   "+roleId);
        List<TreeModel> models = moduleService.queryAllModules(roleId);
        if (null==models){
            return ResultInfo.error("查询错误，请重试!");
        }else {
            return ResultInfo.ok(models,"查询成功");
        }
    }


    /***
     * 查询资源列表
     *
     * @param
     * @return com.yejian.utils.ResultInfo
     */
    @GetMapping("lists")
    public ResultInfo queryModuleList(ModuleQuery moduleQuery){
//        System.out.println("queryModuleList被调用了！");
//        System.out.println("getGrade:"+moduleQuery.getGrade());
//        System.out.println("getMenuName"+moduleQuery.getMenuName());
//        System.out.println("getLimit"+moduleQuery.getLimit());
//        System.out.println("getPage"+moduleQuery.getPage());
        Map<String, Object> map = moduleService.queryModuleList(moduleQuery);
//        System.out.println(map==null);
        if (null==map){
            return ResultInfo.error("Error!");
        }else {
            return ResultInfo.ok(map,"Success!");
        }
    }

    @GetMapping("count")
    public ResultInfo getModuleCount(){
        Integer count = moduleService.getModuleCount();
        if (null==count){
            return ResultInfo.error("查询失败!");
        }else {
            return ResultInfo.ok(count,"查询成功！");
        }
    }

    /**
     * 添加资源
     *
     * @param module
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("addModule")
    public ResultInfo addModule(@RequestBody Module module){
//        System.out.println(module.getModuleName());
//        System.out.println(module.getParentId());
//        System.out.println(module.getGrade());
//        System.out.println(module.getOptValue());
//        System.out.println(module.getOrders());
        moduleService.addModule(module);
        return ResultInfo.ok("添加成功");
    }

    /**
     * 修改资源
     *
     * @param module
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("updateModule")
    public ResultInfo updateModule(@RequestBody Module module){
        moduleService.updateModule(module);
        return ResultInfo.ok("修改成功!");
    }

    /**
     * 删除资源
     *
     * @param id
     * @return com.yejian.utils.ResultInfo
     */
    @PostMapping("deleteModule")
    public ResultInfo deleteModule(@RequestBody Integer id){
        moduleService.deleteModule(id);
        return ResultInfo.ok("删除成功!");
    }


    /**
     * 判断当前模块下模块名称是否重复
     *
     * @param module
     * @return
     */
    @PostMapping("queryModuleByGradeAndModuleName")
    public ResultInfo queryModuleByGradeAndModuleName(@RequestBody Module module){
        String moduleName = module.getModuleName();
        Integer grade = module.getGrade();
        Module module1 = moduleService.queryModuleByGradeAndModuleName(grade, moduleName);
//        System.out.println(module1);
//        System.out.println(grade);
        System.out.println(moduleName);
        System.out.println(null==module1);
        if (null==module1){
            return ResultInfo.ok("2333");
        }else {
            return ResultInfo.error("3222");
        }
    }
    /**
     * 判断当前模块下url是否重复
     *
     * @param module
     * @return
     */
    @PostMapping("queryModuleByGradeAndUrl")
    public ResultInfo queryModuleByGradeAndUrl(@RequestBody Module module){
        String moduleName = module.getModuleName();
        String url = module.getUrl();
        Integer grade = module.getGrade();
        Module module1 = moduleService.queryModuleByGradeAndUrl(grade, url);
        System.out.println(module1);
        System.out.println(grade);
        System.out.println(moduleName);
        System.out.println(null==module1);
        if (null==module1){
            return ResultInfo.ok("2333");
        }else {
            return ResultInfo.error("3222");
        }
    }

    /**
     * 判断权限码是否重复
     *
     * @param module
     * @return
     */
    @PostMapping("queryModuleByOptValue")
    public ResultInfo queryModuleByOptValue(@RequestBody Module module){
        Module module1 = moduleService.queryModuleByOptValue(module);
        System.out.println(module.getOptValue());
        if (null==module1){
            return ResultInfo.ok("");
        }else {
            return ResultInfo.error("");
        }
    }
}
