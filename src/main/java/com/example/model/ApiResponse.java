package com.example.model;


/**
 * @author daniel.94 <daniel.94@kakaocorp.com>
 */
public class ApiResponse {
    private String msg;
    private Object result;
    public ApiResponse() {

    }
    public ApiResponse(Object result){
        this.msg = "success";
        this.result = result;
    }
    public ApiResponse error(Throwable e){
        this.msg = e.getMessage();
        this.result = null;
        return this;
    }
    public ApiResponse error(String e){
        this.msg = e;
        this.result = null;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
