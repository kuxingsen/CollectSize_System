package com.yiban.entity;


/**
 * 学生提交请假要求后的结果
 */
public class Result<T> {
    //提交是否成功
    private boolean success;
    //错误信息
    private String msg;
    //成功状态码
    private int code;
    //数据对象
    private T rows;


    public Result(Dictionary dictionary) {
        this.success = dictionary.isSuccess();
        this.msg = dictionary.getStateInfo();
        this.code=dictionary.getCode();
    }

    public Result(Dictionary dictionary, T rows) {
        this.success = dictionary.isSuccess();
        this.msg = dictionary.getStateInfo();
        this.code=dictionary.getCode();
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success='" + success + '\'' +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", rows='" + rows + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
