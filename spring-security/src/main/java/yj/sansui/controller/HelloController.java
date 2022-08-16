package yj.sansui.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yj.sansui.result.Result;
import yj.sansui.service.impl.VerifyServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sansui
 */
@RestController
public class HelloController {
    private VerifyServiceImpl verifyService;
    @GetMapping("/hello")
    public String hello(){
        return  "hello";
    }

    @GetMapping("/getCode")
    public void getCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        verifyService.createCode(response, request);
    }

    @GetMapping("/checkCode")
    public Result checkCode(String code){
        return verifyService.checkCode(code);
    }

}
