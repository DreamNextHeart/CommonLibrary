package yj.sansui.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.*;
import yj.sansui.bean.dto.UserDTO;
import yj.sansui.result.Result;
import yj.sansui.service.UserService;
import yj.sansui.utils.VerifyCode;

import javax.annotation.Resource;
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
    public Result loginInRemember(UserDTO userDTO){
        return userService.loginInRemember(userDTO);
    }

    @PostMapping("/register")
    public Result register(UserDTO userDTO){
        return userService.register(userDTO);
    }

    @GetMapping("/logout")
    public void logout(){
        StpUtil.logout(1);
    }

}
