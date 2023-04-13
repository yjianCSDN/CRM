package com.yejian.service;

import com.yejian.bean.SaleChance;
import com.yejian.bean.query.SaleChanceQuery;

import java.util.Map;

/**
 * @Author
 * @Date
 */
public interface SaleChanceService {

    Integer  getAll();

    Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery);

    void addSaleChance(SaleChance saleChance);

    int deleteSaleChance(Integer id);

    void updateSaleChance(SaleChance saleChance);

    void updateSaleChanceDevResult(Integer id, Integer devResult);
}
