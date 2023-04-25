package com.yejian.mapper;

import com.yejian.bean.Notice;
import com.yejian.utils.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author
 * @Date
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice,Integer> {

    List<Notice> getUserAll(Integer userId);
}
