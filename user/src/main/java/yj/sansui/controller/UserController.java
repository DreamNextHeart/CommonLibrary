package yj.sansui.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.*;
import yj.sansui.bean.dto.UserDTO;
import yj.sansui.bean.entity.User;
import yj.sansui.constant.Constant;
import yj.sansui.result.Result;
import yj.sansui.service.UserService;
import yj.sansui.utils.VerifyCode;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author sansui
 */
@CrossOrigin
@RestController
public class UserController {
    @Resource
    public UserService userService;

    @GetMapping("/getCode")
    public void getCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        VerifyCode.createCode(response, request);
    }

    @PostMapping("/loginInNotRemember")
    public Map<String,Object> loginInNotRemember(UserDTO userDTO){
        return userService.loginInNotRemember(userDTO);
    }

    @PostMapping("/loginInRemember")
    public Map<String, Object> loginInRemember(UserDTO userDTO){
        return userService.loginInRemember(userDTO);
    }

    @PostMapping("/register")
    public Result register(UserDTO userDTO){
        return userService.register(userDTO);
    }

    @GetMapping("/active")
    public void active(String code, HttpServletResponse response)throws Exception{
        Result result=userService.active(code);
        if(result.getCode()==200){
            Cookie cookie=new Cookie("message","激活成功，请登录");
            response.addCookie(cookie);
            response.sendRedirect(Constant.LOGIN_URL);
        }else if(result.getCode()==203){
            Cookie cookie=new Cookie("message","账号已激活，请登录");
            response.addCookie(cookie);
            response.sendRedirect(Constant.LOGIN_URL);
        }else {
            response.sendRedirect("");
        }
    }

    @GetMapping("/getUserInfo")
    public User getUserInfo(String token){

        return userService.getUserInfo(token);
    }

}
