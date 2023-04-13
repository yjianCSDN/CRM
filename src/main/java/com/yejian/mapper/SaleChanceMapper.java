package com.yejian.mapper;

import com.yejian.bean.SaleChance;
import com.yejian.utils.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author
 * @Date
 */
@Mapper
public interface SaleChanceMapper extends BaseMapper<SaleChance,Integer> {


    Integer delSaleChance(Integer id);

    Integer getAll();

}
