package com.yejian.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yejian.bean.Dictionary;
import com.yejian.bean.query.DictionaryQuery;
import com.yejian.mapper.DictionaryMapper;
import com.yejian.service.DictionaryService;
import com.yejian.utils.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author
 * @Date
 */
@Service
public class DictionaryServiceImpl extends BaseService<Dictionary,Integer> implements DictionaryService {

    @Autowired
    private DictionaryMapper dictionaryMapper;

    /**
     * 多条件查询
     * @param dictionaryQuery
     * @return
     */
    @Override
    public Map<String,Object> queryByParams(DictionaryQuery dictionaryQuery){

        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(dictionaryQuery.getPage(),dictionaryQuery.getLimit());
        PageInfo<Dictionary> pageInfo =new PageInfo<>(dictionaryMapper.selectByParams(dictionaryQuery));
//        AssertUtil.isTrue(map==null,"查询出错，请重试");
        map.put("code",200);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());

        return map;
    }

    /**
     * 更新字典数据
     * @param dictionary
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateDictionary(Dictionary dictionary){
        dictionary.setUpdateDate(new Date());
        return dictionaryMapper.updateByPrimaryKeySelective(dictionary);
    }

    /**
     * 删除字典数据
     * @param id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer delDictionary(Integer id){
//        Dictionary dictionary = dictionaryMapper.selectByPrimaryKey(id);
//        dictionary.setUpdateDate(new Date());
        return dictionaryMapper.delDictionary(id);
    }

    /**
     * 根据id查找字典实体
     * @param id
     * @return
     */
    @Override
    public Dictionary queryById(Integer id){
        return dictionaryMapper.selectByPrimaryKey(id);
    }

    /**
     * 插入字典数据
     * @param dictionary
     * @return java.lang.Integer
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer insertDictionary(Dictionary dictionary){
        dictionary.setCreateDate(new Date());
        dictionary.setUpdateDate(new Date());
        dictionary.setIsValid(1);
        return dictionaryMapper.insertSelective(dictionary);
    }
}
