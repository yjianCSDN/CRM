package com.yejian.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yejian.bean.Notice;
import com.yejian.bean.query.MyNoticeQuery;
import com.yejian.bean.query.NoticeQuery;
import com.yejian.mapper.NoticeMapper;
import com.yejian.service.NoticeService;
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
public class NoticeServiceImpl extends BaseService<Notice,Integer> implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public Map<String, Object> queryByParams(NoticeQuery noticeQuery) {
        Map<String, Object> map = new HashMap<>();
        // 开启分页
        PageHelper.startPage(noticeQuery.getPage(), noticeQuery.getLimit());
        // 得到对应分页对象
        PageInfo<Notice> pageInfo = new PageInfo<>(noticeMapper.selectByParams(noticeQuery));

        // 设置map对象
        map.put("code",200);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data",pageInfo.getList());
        return map;

    }

    /**
     * 查看我的发布消息
     * @param myNoticeQuery
     * @return
     */
    @Override
    public Map<String, Object> getUserAll(MyNoticeQuery myNoticeQuery) {
        Map<String, Object> map = new HashMap<>();
        // 开启分页
        PageHelper.startPage(myNoticeQuery.getPage(), myNoticeQuery.getLimit());
        // 得到对应分页对象
        PageInfo<Notice> pageInfo = new PageInfo<>(noticeMapper.getUserAll(myNoticeQuery.getUserId()));

        // 设置map对象
        map.put("code",200);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data",pageInfo.getList());
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer delNotice(Integer id) {
        Notice notice = noticeMapper.selectByPrimaryKey(id);
        notice.setIsValid(0);
        notice.setUpdateDate(new Date());
        Integer integer = noticeMapper.updateByPrimaryKeySelective(notice);
        return integer;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateNotice(Notice notice) {
        notice.setUpdateDate(new Date());
        Integer integer = noticeMapper.updateByPrimaryKeySelective(notice);
        return integer;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer addNotice(Notice notice) {
        notice.setIsValid(1);
        notice.setCreateDate(new Date());
        notice.setUpdateDate(new Date());
        Integer integer = noticeMapper.insertSelective(notice);
        return integer;
    }
}
