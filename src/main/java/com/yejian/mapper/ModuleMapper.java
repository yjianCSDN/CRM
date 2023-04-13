package com.yejian.mapper;

import com.yejian.bean.Module;
import com.yejian.bean.model.TreeModel;
import com.yejian.bean.query.ModuleQuery;
import com.yejian.utils.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author
 * @Date
 */
@Mapper
public interface ModuleMapper extends BaseMapper<Module,Integer> {

    // 查询所有的资源列表
     List<TreeModel> queryAllModules();

    // 查询所有的资源数据
     List<Module> queryModuleList(ModuleQuery moduleQuery);

    // 通过层级与模块名查询资源对象
    Module queryModuleByGradeAndModuleName(@Param("grade") Integer grade, @Param("moduleName") String moduleName);

    // 通过层级与URL查询资源对象
    Module queryModuleByGradeAndUrl(@Param("grade")Integer grade, @Param("url")String url);

    // 通过权限码查询资源对象
    Module queryModuleByOptValue(String optValue);

    // 查询指定资源是否存在子记录
    Integer queryModuleByParentId(Integer id);

    Integer getModuleCount();
}
