package yj.sansui.service;


import yj.sansui.result.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sansui
 */
public interface VerifyService {
    /**
     * createCode 生成验证码
     * @param response HttpServletResponse
     * @param request HttpServletRequest
     * @throws IOException 输入异常
     */
    void createCode(HttpServletResponse response, HttpServletRequest request) throws IOException;

    /**
     * checkCode,验证验证码
     * @param verificationCode String
     * @return Result
     */
    Result checkCode(String verificationCode);
}
