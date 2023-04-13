package com.yejian.mapper;

import com.yejian.bean.Dictionary;
import com.yejian.utils.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author
 * @Date
 */
@Mapper
public interface DictionaryMapper extends BaseMapper<Dictionary,Integer> {

    Integer delDictionary(Integer id);

    List<Dictionary> selectList();

    Dictionary queryByParams(Integer id);

}
