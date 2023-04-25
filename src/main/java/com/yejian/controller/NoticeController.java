package com.yejian.controller;

import com.yejian.bean.Notice;
import com.yejian.bean.User;
import com.yejian.bean.query.NoticeQuery;
import com.yejian.service.NoticeService;
import com.yejian.service.UserService;
import com.yejian.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author
 * @Date
 */
@RestController
@RequestMapping("notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UserService userService;

    @GetMapping("lists")
    public ResultInfo queryByParams(NoticeQuery noticeQuery,Integer flag,HttpServletRequest request){
        if (null!=flag && flag==1){
            User user = userService.queryUserByUserName(request.getHeader("userName"));
            System.out.println(user);
            noticeQuery.setUserId(user.getId());
        }
        Map<String, Object> map = noticeService.queryByParams(noticeQuery);
        if (map==null){
            return ResultInfo.error("查询失败");
        }else {
            return ResultInfo.ok(map,"查询成功");
        }
    }

    @PostMapping("add")
    public ResultInfo insertNotice(@RequestBody Notice notice, HttpServletRequest request){
        String userName = request.getHeader("userName");
        User user = userService.queryUserByUserName(userName);
        notice.setUserId(user.getId());
        notice.setPublisher(user.getUserName());
        Integer integer = noticeService.addNotice(notice);
        if (integer<1){
            return ResultInfo.error("添加失败");
        }else {
            return ResultInfo.ok("添加成功");
        }
    }

    private static ResultInfo returnMessage(Integer integer,String message){
        if (integer<1){
            return ResultInfo.error(message+"失败");
        }else {
            return ResultInfo.ok(message+"成功");
        }
    }

    @PostMapping("delete")
    public ResultInfo delNotice(@RequestBody Integer id){
        Integer integer = noticeService.delNotice(id);
        return returnMessage(integer,"删除");
    }

    @PostMapping("update")
    public ResultInfo updateNotice(@RequestBody Notice notice){
        Integer integer = noticeService.updateNotice(notice);
        return returnMessage(integer,"更新");
    }

}
