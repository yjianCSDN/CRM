package com.yejian.service;

import com.yejian.bean.Dictionary;
import com.yejian.bean.query.DictionaryQuery;

import java.util.Map;

/**
 * @Author
 * @Date
 */
public interface DictionaryService {
    Map<String,Object> queryByParams(DictionaryQuery dictionaryQuery);

    Integer updateDictionary(Dictionary dictionary);

    Integer delDictionary(Integer id);

    Dictionary queryById(Integer id);

    Integer insertDictionary(Dictionary dictionary);
}
