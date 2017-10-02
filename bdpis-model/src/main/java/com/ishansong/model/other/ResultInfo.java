package com.ishansong.model.other;

/**
 * Created by yangguoliang on 2017/6/7.
 * <p>
 * 定义返回的对象
 */
public class ResultInfo {

    //返回的结果内容
    private Object result;
    //信息描述
    private String message;
    //信息编码
    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
