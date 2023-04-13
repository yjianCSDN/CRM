package com.yejian.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yejian.bean.CustomerReprieve;
import com.yejian.bean.query.CustomerReprieveQuery;
import com.yejian.mapper.CustomerLossMapper;
import com.yejian.mapper.CustomerReprieveMapper;
import com.yejian.service.CustomerReprieveService;
import com.yejian.utils.AssertUtil;
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
public class CustomerReprieveServiceImpl extends BaseService<CustomerReprieve,Integer> implements CustomerReprieveService {

    @Autowired
    private CustomerReprieveMapper customerReprieveMapper;
    @Autowired
    private CustomerLossMapper customerLossMapper;


    /**
     * 分页条件查询流失客户暂缓操作的列表
     *
     * @param customerReprieveQuery
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> queryCustomerReprieveByParams(CustomerReprieveQuery customerReprieveQuery) {
        Map<String, Object> map = new HashMap<>();

        // 开启分页
        PageHelper.startPage(customerReprieveQuery.getPage(), customerReprieveQuery.getLimit());
        // 得到对应分页对象
        PageInfo<CustomerReprieve> pageInfo = new PageInfo<>(customerReprieveMapper.selectByParams(customerReprieveQuery));

        // 设置map对象
        map.put("code",200);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data",pageInfo.getList());

        return map;
    }

    /**
     * 添加暂缓数据
     *  1. 参数校验
     *      流失客户ID  lossId
     *          非空，数据存在
     *      暂缓措施内容 measure
     *          非空
     *  2. 设置参数的默认值
     *      是否有效
     *          默认有效，1
     *      创建时间
     *          系统当前时间
     *      修改时间
     *          系统当前时间
     *  3. 执行添加操作，判断受影响的行数
     *
     * @param customerReprieve
     * @return Integer
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer addCustomerRepr(CustomerReprieve customerReprieve) {

        /* 1. 参数校验 */
//        checkParams(customerReprieve.getLossId(), customerReprieve.getMeasure());

        /* 2. 设置参数的默认值 */
        customerReprieve.setIsValid(1);
        customerReprieve.setCreateDate(new Date());
        customerReprieve.setUpdateDate(new Date());

        Integer integer = customerReprieveMapper.insertSelective(customerReprieve);
        return integer;
        /* 3. 执行添加操作，判断受影响的行数 */
//        AssertUtil.isTrue(customerReprieveMapper.insertSelective(customerReprieve) < 1, "添加暂缓数据失败！");
    }

    /**
     * 修改暂缓数据
     *  1. 参数校验
     *      主键ID    id
     *          非空，数据存在
     *      流失客户ID  lossId
     *          非空，数据存在
     *      暂缓措施内容 measure
     *          非空
     *  2. 设置参数的默认值
     *      修改时间
     *          系统当前时间
     *  3. 执行修改操作，判断受影响的行数
     *
     * @param customerReprieve
     * @return Integer
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateCustomerRepr(CustomerReprieve customerReprieve) {
        /* 1. 参数校验 */
        // 主键ID    id
        AssertUtil.isTrue(null == customerReprieve.getId()
                || customerReprieveMapper.selectByPrimaryKey(customerReprieve.getId()) == null, "待更新记录不存在！");
        // 参数校验
//        checkParams(customerReprieve.getLossId(), customerReprieve.getMeasure());

        /* 2. 设置参数的默认值 */
        customerReprieve.setUpdateDate(new Date());
        Integer integer = customerReprieveMapper.updateByPrimaryKeySelective(customerReprieve);
        return integer;
        /* 3. 执行修改操作，判断受影响的行数 */
//        AssertUtil.isTrue(customerReprieveMapper.updateByPrimaryKeySelective(customerReprieve) < 1, "修改暂缓数据失败！");

    }

    /**
     * 删除暂缓数据
     *  1. 判断id是否为空，且数据存在
     *  2. 设置isvalid为0
     *  3. 执行更新操作，判断受影响的行数
     *
     * @param id
     * @return Integer
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer deleteCustomerRepr(Integer id) {
        // 判断id是否为空
//        AssertUtil.isTrue(null == id, "待删除记录不存在！");
        // 通过id查询暂缓数据
        CustomerReprieve customerReprieve = customerReprieveMapper.selectByPrimaryKey(id);
        // 判断数据是否存在
        AssertUtil.isTrue(null == customerReprieve, "待删除记录不存在！");

        // 设置isValid
        customerReprieve.setIsValid(0);
        customerReprieve.setUpdateDate(new Date());
        Integer integer = customerReprieveMapper.updateByPrimaryKeySelective(customerReprieve);
        return integer;
        // 执行更新操作，判断受影响的行数
//        AssertUtil.isTrue(customerReprieveMapper.updateByPrimaryKeySelective(customerReprieve) < 1, "删除暂缓数据失败！");
    }
}
