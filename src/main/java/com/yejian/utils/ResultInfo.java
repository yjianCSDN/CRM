package com.yejian.utils;

import lombok.Data;

/**
 * 设置返回数据类型
 */
@Data
public class ResultInfo {

    private Integer code=200;
    private String msg="success";
    private Object result;

    public static ResultInfo error(String msg) {
        return error(Result.SC_INTERNAL_SERVER_ERROR, msg);
    }


    public static ResultInfo error(int code, String msg) {
        ResultInfo r = new ResultInfo();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static ResultInfo ok(String msg) {
        ResultInfo r = new ResultInfo();
        r.setMsg(msg);
        return r;
    }

    public static ResultInfo ok(Object data) {
        ResultInfo r = new ResultInfo();
        r.setResult(data);
        return r;
    }
    public static ResultInfo ok(Object data,String msg) {
        ResultInfo r = new ResultInfo();
        r.setMsg(msg);
        r.setResult(data);
        return r;
    }
    public static ResultInfo ok() {
        return new ResultInfo();
    }


}
