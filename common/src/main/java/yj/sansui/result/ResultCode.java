package yj.sansui.result;

import lombok.Getter;

/**
 * ResultCode，枚举类，自定义响应结果的响应值和响应信息
 * @author Sansui
 * @annotation Getter，自动根据字段生成get方法
 */
@Getter
public enum ResultCode implements StatusCode{
    /**
     * SUCCESS：请求成功
     * FAILED：请求失败
     * VALIDATE_ERROR：参数校验错误
     * RESPONSE_PACK_ERROR：response结果封装错误
     */
    SUCCESS(200,"请求成功"),
    FAILED(201,"请求失败"),
    VALIDATE_ERROR(202,"参数校验错误"),
    RESPONSE_PACK_ERROR(203,"response结果封装错误");

    /**
     * code，状态码
     */
    private Integer code;

    /**
     * message，响应信息
     */
    private String message;

    /**
     * 全参构造方法
     * @param code Integer
     * @param message String
     */
    ResultCode(Integer code,String message){
        this.code=code;
        this.message=message;
    }
}
