package com.yejian.service;

import com.yejian.bean.Notice;
import com.yejian.bean.query.MyNoticeQuery;
import com.yejian.bean.query.NoticeQuery;

import java.util.Map;

/**
 * @Author
 * @Date
 */
public interface NoticeService {

     Map<String,Object> queryByParams(NoticeQuery noticeQuery);

    Map<String, Object> getUserAll(MyNoticeQuery myNoticeQuery);

     Integer delNotice(Integer id);

     Integer updateNotice(Notice notice);

     Integer addNotice(Notice notice);
}
