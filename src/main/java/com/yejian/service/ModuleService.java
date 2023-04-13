package com.yejian.service;

import com.yejian.bean.Module;
import com.yejian.bean.model.TreeModel;
import com.yejian.bean.query.ModuleQuery;

import java.util.List;
import java.util.Map;

/**
 * @Author
 * @Date
 */
public interface ModuleService {

    List<TreeModel> queryAllModules(Integer roleId);

    Map<String,Object> queryModuleList(ModuleQuery moduleQuery);

    void addModule(Module module);

    void updateModule(Module module);

    void deleteModule(Integer id);

    Integer getModuleCount();

    Module queryModuleByGradeAndModuleName(Integer grade,String moduleName);

    Module queryModuleByGradeAndUrl(Integer grade,String url);

    Module queryModuleByOptValue(Module module);
}
