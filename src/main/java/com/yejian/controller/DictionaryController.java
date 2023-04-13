package com.yejian.controller;

import com.yejian.bean.Dictionary;
import com.yejian.bean.query.DictionaryQuery;
import com.yejian.service.DictionaryService;
import com.yejian.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author
 * @Date
 */
@RestController
@RequestMapping("Dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 多条件查询字典数据（分页）
     * @param dictionaryQuery
     * @return com.yejian.utils.ResultInfo
     */
    @GetMapping("lists")
    public ResultInfo getDictionaryList(DictionaryQuery dictionaryQuery){
        System.out.println("dictionary:--->"+dictionaryQuery);
        Map<String, Object> map = dictionaryService.queryByParams(dictionaryQuery);
        if (null==map){
            return ResultInfo.error("查询失败");
        }else {
            return ResultInfo.ok(map,"查找成功");
        }
    }


    @PostMapping("addDictionary")
    public ResultInfo addDictionary(@RequestBody Dictionary dictionary){
        System.out.println(dictionary);
        Integer integer = dictionaryService.insertDictionary(dictionary);
        if (integer<1){
            return ResultInfo.error("添加失败");
        }else {
            return ResultInfo.ok("添加成功");
        }
//        return ResultInfo.ok("添加成功");
    }

    @PostMapping("updateDictionary")
    public ResultInfo updateDictionary(@RequestBody Dictionary dictionary){
        Integer integer = dictionaryService.updateDictionary(dictionary);
        if (integer<1){
            return ResultInfo.error("更新失败");
        }else {
            return ResultInfo.ok("更新成功");
        }
    }
    
    @PostMapping("delDictionary")
    public ResultInfo delDictionary(@RequestBody Integer id){
        System.out.println(id);
        Integer integer = dictionaryService.delDictionary(id);
        if (integer<1){
            return ResultInfo.error("添加失败");
        }else {
            return ResultInfo.ok("添加成功");
        }
//        return ResultInfo.ok("删除成功");
    }

}
