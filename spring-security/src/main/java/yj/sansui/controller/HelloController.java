package yj.sansui.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author sansui
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return  "hello";
    }


}
