package com.yejian.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yejian.bean.Customer;
import com.yejian.bean.CustomerLoss;
import com.yejian.bean.query.CustomerLossQuery;
import com.yejian.mapper.CustomerLossMapper;
import com.yejian.mapper.CustomerMapper;
import com.yejian.service.CustomerLossService;
import com.yejian.utils.AssertUtil;
import com.yejian.utils.BaseService;
import org.apache.commons.lang3.StringUtils;
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
public class CustomerLossServiceImpl extends BaseService<CustomerLoss,Integer> implements CustomerLossService {

    @Autowired
    private CustomerLossMapper customerLossMapper;

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 分页条件查询
     *
     * @param customerLossQuery
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> queryCustomerLossByParams(CustomerLossQuery customerLossQuery) {

        Map<String, Object> map = new HashMap<>();

        // 开启分页
        PageHelper.startPage(customerLossQuery.getPage(), customerLossQuery.getLimit());
        // 得到对应分页对象
        PageInfo<CustomerLoss> pageInfo = new PageInfo<>(customerLossMapper.selectByParams(customerLossQuery));

        // 设置map对象
        map.put("code",200);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data",pageInfo.getList());

        return map;
    }

    /**
     * 更新流失客户的流失状态
     *  1. 参数校验
     *      判断id非空且对应的数据存在
     *      流失原因非空
     *  2. 设置参数的默认值
     *      设置流失状态  state=1  0=暂缓流失，1=确认流失
     *      流失原因
     *      客户流失时间  系统当前时间
     *      更新时间     系统当前时间
     *  3. 执行更新操作，判断受影响的行数
     *
     * @param id
     * @param lossReason
     * @return void
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateCustomerLossStateById(Integer id, String lossReason) {
        /* 1. 参数校验 */
        // 判断id非空
        AssertUtil.isTrue(null == id, "待确认流失的客户不存在！");
        // 通过id查询流失客户的记录
        CustomerLoss customerLoss = customerLossMapper.selectByPrimaryKey(id);
        // 判断流失客户记录是否存在
        AssertUtil.isTrue(null == customerLoss, "待确认流失的客户不存在！");
        // 流失原因非空
        AssertUtil.isTrue(StringUtils.isBlank(lossReason), "流失原因不能为空！");

        /* 2. 设置参数的默认值 */
        // 设置流失状态  state=1  0=暂缓流失，1=确认流失
        customerLoss.setState(1);
        // 设置流失原因
        customerLoss.setLossReason(lossReason);
        // 客户流失时间  系统当前时间
        customerLoss.setConfirmLossTime(new Date());
        // 更新时间     系统当前时间
        customerLoss.setUpdateDate(new Date());

        //将该客户设置为流失（isValid=0）
        Customer customer = customerMapper.queryCustomerByName(customerLoss.getCusName());
        customer.setIsValid(0);
        customerMapper.updateByPrimaryKeySelective(customer);

        /* 3. 执行更新操作，判断受影响的行数 */
        Integer integer = customerLossMapper.updateByPrimaryKeySelective(customerLoss);
        return integer;
//        AssertUtil.isTrue(customerLossMapper.updateByPrimaryKeySelective(customerLoss) < 1, "确认流失失败！");
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateCustLossInfo(CustomerLoss customerLoss){
        customerLoss.setUpdateDate(new Date());
        Integer integer = customerLossMapper.updateByPrimaryKeySelective(customerLoss);
        return integer;
    }

    /**
     * 添加客户流失状态
     * @param customerLoss
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer insertLossInfo(CustomerLoss customerLoss){
        Customer customer = customerMapper.queryCustomerByName(customerLoss.getCusName());
        customerLoss.setCusNo(customer.getKhno());
        customerLoss.setCusManager(customer.getCusManager());
        customerLoss.setCreateDate(new Date());
        customerLoss.setUpdateDate(new Date());
        customerLoss.setIsValid(0);//0暂缓流失 1确认流失
        Integer integer1 = customerLossMapper.insertSelective(customerLoss);
        return integer1;
    }
}
