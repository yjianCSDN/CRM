package com.yejian.service;

import com.yejian.bean.CustomerLoss;
import com.yejian.bean.query.CustomerLossQuery;

import java.util.Map;

/**
 * @Author
 * @Date
 */
public interface CustomerLossService {

    Map<String, Object> queryCustomerLossByParams(CustomerLossQuery customerLossQuery);

    Integer updateCustomerLossStateById(Integer id, String lossReason);

    Integer updateCustLossInfo(CustomerLoss customerLoss);

    Integer insertLossInfo(CustomerLoss customerLoss);
}
