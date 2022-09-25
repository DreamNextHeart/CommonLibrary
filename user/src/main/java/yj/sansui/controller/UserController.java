package yj.sansui.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.web.bind.annotation.*;
import yj.sansui.bean.dto.UserDTO;
import yj.sansui.bean.entity.User;
import yj.sansui.result.Result;
import yj.sansui.service.UserService;
import yj.sansui.utils.VerifyCode;
import yj.sansui.version2.RedisUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
    public Result loginInRemember(UserDTO userDTO){
        return userService.loginInRemember(userDTO);
    }

    @PostMapping("/register")
    public Result register(User user){
        return userService.register(user);
    }

    @GetMapping("/logout")
    public void logout(){
        StpUtil.logout(1);
    }

}
