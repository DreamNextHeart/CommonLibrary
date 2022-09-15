package yj.sansui.controller;

import org.springframework.web.bind.annotation.*;
import yj.sansui.bean.dto.UserDTO;
import yj.sansui.bean.entity.User;
import yj.sansui.result.Result;
import yj.sansui.service.UserService;
import yj.sansui.utils.VerifyCode;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @PostMapping("/loginIn")
    public Result loginIn(UserDTO userDTO){
        return userService.loginIn(userDTO);
    }

    @PostMapping("/register")
    public Result register(User user){
        return userService.register(user);
    }
}
