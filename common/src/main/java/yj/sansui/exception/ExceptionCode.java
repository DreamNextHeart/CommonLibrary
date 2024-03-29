package yj.sansui.exception;

import lombok.Getter;
import yj.sansui.result.StatusCode;


/**
 * ExceptionCode 异常码枚举类
 *
 * @author Sansui
 * @annotation Getter 自动生成get方法
 */
@Getter
public enum ExceptionCode implements StatusCode {
    /**
     * BUSINESS_EXCEPTION：业务异常
     * PRICE_EXCEPTION：价格异常
     * ID_NULL_EXCEPTION：id空异常
     * SIGNATURE_NOT_MATCH：签名不匹配
     */
    BUSINESS_EXCEPTION(300, "业务异常"),
    PRICE_EXCEPTION(301, "价格异常"),
    ID_NULL_EXCEPTION(302, "id空异常"),
    SIGNATURE_NOT_MATCH(303, "签名不匹配"),
    SIGNATURE_IS_NULL(304, "签名为空"),
    SIGNATURE_DECODE_EXCEPTION(305, "签名解析失败"),
    DELETE_EXCEPTION(306,"删除失败"),
    SAVE_EXCEPTION(307,"保存失败"),
    UPDATE_EXCEPTION(308,"更新失败"),
    SELECT_EXCEPTION(309,"查询失败"),
    CODE_EXCEPTION(310,"验证码错误"),
    USERNAME_EXCEPTION(311,"用户名或密码错误"),
    TOKEN_OUT_OF_DATE(312,"token已过期"),
    PHONE_EXIST(313,"手机号码已被注册"),
    SEND_MAIL_ERROR(314,"邮件发送失败"),
    ACTIVE_CODE_INEXISTENCE(315,"激活码不存在"),
    RESPONSE_PACK_ERROR(316,"response结果封装错误"),
    ACCOUNT_UN_ACTIVE(317,"账号未激活");


    /**
     * code，异常码，用于标识异常类型
     * message，异常信息，用于解释异常详细信息
     */
    private Integer code;
    private String message;

    /**
     * 全参构造函数
     *
     * @param code    Integer
     * @param message String
     */
    ExceptionCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
