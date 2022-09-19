package yj.sansui.exception;

import lombok.Getter;
import yj.sansui.result.StatusCode;

/**
 * CommonException，通用异常类
 *
 * @author Sansui
 * @annotation Getter 自动生成get方法
 */
@Getter
public class CommonException extends RuntimeException {
    /**
     * code，异常码
     * message，异常信息
     */
    private Integer code;
    private String message;

    /**
     * 全参构造函数，用于手动设置抛出异常
     *
     * @param statusCode StatusCode
     * @param message    String
     */
    public CommonException(StatusCode statusCode, String message) {
        // message用于用户设置抛出错误详情，例如：当前价格-5，小于0，给开发人员看的
        super(message);
        // 异常码
        this.code = statusCode.getCode();
        // 异常码配套的msg
        this.message = statusCode.getMessage();
    }

    /**
     * 构造函数，默认异常使用BUSINESS_ERROR状态码
     *
     * @param message String
     */
    public CommonException(String message) {
        super(message);
        this.code = ExceptionCode.BUSINESS_EXCEPTION.getCode();
        this.message = message;
    }
}
