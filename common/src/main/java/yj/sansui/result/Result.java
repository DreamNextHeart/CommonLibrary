package yj.sansui.result;

import lombok.Data;

/**
 * Result 统一响应结果
 * 一个通用的响应结果格式应该具备以下信息：
 * 1. 响应码：200，404，500等；
 * 2. 响应信息：请求成功，查询成功，删除失败等；
 * 3. 响应数据：一般为实体对象
 * @author Sansui
 * @annotation Data，自动根据字段生成get/set、toString等方法
 */
@Data
public class Result {
    /**
     * code，Integer，响应码
     * message，String，响应信息
     * data，Object，响应对象
     */
    private Integer code;
    private String message;
    private Object data;

    /**
     *  构造器公有
     */
    public Result() {
    }

    /**
     * Result全参构造函数
     * @param code Integer
     * @param message String
     * @param data Object
     */
    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 默认构造函数
     * 返回成功响应码和响应信息
     * @param data Object
     */
    public Result(Object data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.message = ResultCode.SUCCESS.getMessage();
        this.data = data;
    }

    /**
     * 返回指定响应码，数据对象
     * @param statusCode StatusCode
     * @param data Object
     */
    public Result(StatusCode statusCode, Object data) {
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
        this.data = data;
    }

    /**
     * 只返回响应码
     * @param statusCode StatusCode
     */
    public Result(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
        this.data = null;
    }

    /**
     * 针对抛出异常进行封装返回
     * @param code Integer
     * @param message String
     */
    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }
}
