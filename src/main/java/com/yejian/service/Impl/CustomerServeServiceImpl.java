package com.yejian.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yejian.bean.Customer;
import com.yejian.bean.CustomerServe;
import com.yejian.bean.query.CustomerServeQuery;
import com.yejian.enums.CustomerServeStatus;
import com.yejian.mapper.CustomerMapper;
import com.yejian.mapper.CustomerServeMapper;
import com.yejian.service.CustomerServeService;
import com.yejian.utils.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author
 * @Date
 */
@Service
public class CustomerServeServiceImpl extends BaseService<CustomerServe,Integer> implements CustomerServeService {

    @Autowired
    private CustomerServeMapper customerServeMapper;
    @Autowired
    public CustomerMapper customerMapper;


    /**
     * 多条件分页查询服务数据列表
     *
     * @param customerServeQuery
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String, Object> queryCustomerServeByParams(CustomerServeQuery customerServeQuery) {
        Map<String, Object> map = new HashMap<>();

        // 开启分页
        PageHelper.startPage(customerServeQuery.getPage(), customerServeQuery.getLimit());
        // 得到对应分页对象
        PageInfo<CustomerServe> pageInfo = new PageInfo<>(customerServeMapper.selectByParams(customerServeQuery));

        // 设置map对象
        map.put("code",200);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data",pageInfo.getList());

        return map;
    }

    @Override
    public Customer getInfoByName(String customer){
        return customerMapper.queryCustomerByName(customer);
    }


    /**
     * 添加服务操作
     *  1. 参数校验
     *      客户名 customer
     *          非空，客户表中存在客户记录
     *      服务类型 serveType
     *          非空
     *      服务请求内容  serviceRequest
     *          非空
     *  2. 设置参数的默认值
     *      服务状态
     *          服务创建状态  fw_001
     *      是否有效
     *      创建时间
     *      更新时间
     *      创建人 createPeople
     *          （前端页面中通过cookie获取，传递到后台）
     *  2. 执行添加操作，判断受影响的行数
     *
     * @param customerServe
     * @return void
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer addCustomerServe(CustomerServe customerServe) {

        /* 1. 参数校验 */
        // 客户名 customer     非空
//        AssertUtil.isTrue(StringUtils.isBlank(customerServe.getCustomer()), "客户名不能为空！");
        // 客户名 customer     客户表中存在客户记录
//        AssertUtil.isTrue(customerMapper.queryCustomerByName(customerServe.getCustomer()) == null, "客户不存在！" );

        // 服务类型 serveType  非空
//        AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServeType()), "请选择服务类型！");

        //  服务请求内容  serviceRequest  非空
//        AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceRequest()), "服务请求内容不能为空！");

        /* 2. 设置参数的默认值 */
        //  服务状态    服务创建状态  fw_001
        customerServe.setState(CustomerServeStatus.ASSIGNED.getState());
        customerServe.setIsValid(1);
        customerServe.setCreateDate(new Date());
        customerServe.setUpdateDate(new Date());
        System.out.println(customerServe.getCreatePeople());
        Integer integer = customerServeMapper.insertSelective(customerServe);
        return integer;
        /* 2. 执行添加操作，判断受影响的行数 */
//        AssertUtil.isTrue(customerServeMapper.insertSelective(customerServe) < 1, "添加服务失败！");
    }

    /**
     * 服务分配/服务处理/服务反馈
     *  1. 参数校验与设置参数的默认值
     *      客户服务ID
     *          非空，记录必须存在
     *      客户服务状态
     *          如果服务状态为 服务分配状态 fw_002
     *              分配人
     *                  非空，分配用户记录存在
     *              分配时间
     *                  系统当前时间
     *              更新时间
     *                  系统当前时间
     *
     *          如果服务状态为 服务处理状态 fw_003
     *              服务处理内容
     *                  非空
     *              服务处理时间
     *                  系统当前时间
     *              更新时间
     *                  系统当前时间
     *
     *          如果服务状态是 服务反馈状态  fw_004
     *              服务反馈内容
     *                  非空
     *              服务满意度
     *                  非空
     *              更新时间
     *                  系统当前时间
     *              服务状态
     *                  设置为 服务归档状态 fw_005
     *
     * 2. 执行更新操作，判断受影响的行数
     *
     * @param customerServe
     * @return void
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateCustomerServe(CustomerServe customerServe) {

        // 判断客户服务的服务状态
        if (CustomerServeStatus.ASSIGNED.getState().equals(customerServe.getState())) {
            customerServe.setState(CustomerServeStatus.PROCED.getState());
            if (customerServe.getAssignTime()==null){
                customerServe.setAssignTime(new Date());
            }
        //创建----->（已）分配
        } else if (CustomerServeStatus.PROCED.getState().equals(customerServe.getState())) {
            customerServe.setServiceProceTime(new Date());
            customerServe.setState(CustomerServeStatus.FEED_BACK.getState());
        //分配----->（已）进行处理
        } else if (CustomerServeStatus.FEED_BACK.getState().equals(customerServe.getState())) {
            customerServe.setServiceProceTime(new Date());
            if (customerServe.getAuditStatus()!=2){
                customerServe.setAuditStatus(0);
            }
            customerServe.setState(CustomerServeStatus.ARCHIVED.getState());
        //进行处理----->（已）反馈
        }
//        else if (CustomerServeStatus.FEED_BACK.getState().equals(customerServe.getState())){
//            customerServe.setState(CustomerServeStatus.ARCHIVED.getState());
//        }
        // 更新时间     系统当前时间
        customerServe.setUpdateDate(new Date());
        Integer integer = customerServeMapper.updateByPrimaryKeySelective(customerServe);
        return integer;
    }




    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateCustomerServeCREATED(CustomerServe customerServe){
        if (CustomerServeStatus.CREATED.getState().equals(customerServe.getState())){
            customerServe.setState(CustomerServeStatus.ASSIGNED.getState());
            customerServe.setAssignTime(new Date());
        }
        customerServe.setUpdateDate(new Date());
        Integer integer = customerServeMapper.updateByPrimaryKeySelective(customerServe);
        return integer;
    }
    //

    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateCustomerServeASSIGNED(CustomerServe customerServe){
        if (CustomerServeStatus.ASSIGNED.getState().equals(customerServe.getState())){
            customerServe.setServiceProceTime(new Date());
            customerServe.setState(CustomerServeStatus.PROCED.getState());
        }
        customerServe.setUpdateDate(new Date());
        Integer integer = customerServeMapper.updateByPrimaryKeySelective(customerServe);
        return integer;
    }
    //

    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateCustomerServePROCED(CustomerServe customerServe){
        if (CustomerServeStatus.PROCED.getState().equals(customerServe.getState())){
            customerServe.setServiceProceTime(new Date());
            customerServe.setState(CustomerServeStatus.FEED_BACK.getState());
        }
        customerServe.setUpdateDate(new Date());
        Integer integer = customerServeMapper.updateByPrimaryKeySelective(customerServe);
        return integer;
    }
    //
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateCustomerServeFEED_BACK(CustomerServe customerServe){
        if (CustomerServeStatus.FEED_BACK.getState().equals(customerServe.getState())){
            customerServe.setState(CustomerServeStatus.ARCHIVED.getState());
        }
        customerServe.setUpdateDate(new Date());
        Integer integer = customerServeMapper.updateByPrimaryKeySelective(customerServe);
        return integer;
    }

    @Override
    public Map<String, Object> countCustomerMake02() {
        Map<String, Object> map = new HashMap<>();
        // 查询客户构成数据的列表
        List<Map<String,Object>> dataList = customerServeMapper.countCustomerMake();
        // 折线图X轴数据  数组
        List<String> data1 = new ArrayList<>();
        // 折线图Y轴数据  数组
        List<Integer> data2 = new ArrayList<>();

        // 判断数据列表 循环设置数据
        if (dataList != null && dataList.size() > 0) {
            // 遍历集合
            dataList.forEach(m -> {
                // 获取"level"对应的数据，设置到X轴的集合中
                data1.add(m.get("serve_type").toString());
                // 获取"total"对应的数据，设置到Y轴的集合中
                data2.add(Integer.parseInt(m.get("total").toString()));
            });
        }

        // 将X轴的数据集合与Y轴的数据集合，设置到map中
        map.put("dataFirst",data1);
        map.put("dataSecond",data2);

        return map;
    }

    /**
     * 查询客户构成 （饼状图数据处理）
     *
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> countCustomerMake() {
        Map<String, Object> map = new HashMap<>();
        // 查询客户构成数据的列表
        List<Map<String,Object>> dataList = customerServeMapper.countCustomerMake();
        // 饼状图数据   数组（数组中是字符串）
        List<String> data1 = new ArrayList<>();
        // 饼状图的数据  数组（数组中是对象）
        List<Map<String, Object>> data2 = new ArrayList<>();

        // 判断数据列表 循环设置数据
        if (dataList != null && dataList.size() > 0) {
            // 遍历集合
            dataList.forEach(m -> {
                // 饼状图数据   数组（数组中是字符串）
                data1.add(m.get("serve_type").toString());
                // 饼状图的数据  数组（数组中是对象）
                Map<String,Object> dataMap = new HashMap<>();
                dataMap.put("name", m.get("serve_type"));
                dataMap.put("value", m.get("total"));
                data2.add(dataMap);
            });
        }

        // 将X轴的数据集合与Y轴的数据集合，设置到map中
        map.put("data1",data1);
        map.put("data2",data2);

        return map;
    }

    @Override
    public List<CustomerServe> getServeByAssigner(Integer id) {
        List<CustomerServe> serve = customerServeMapper.getServeByAssigner(id);
        return serve;
    }

}
