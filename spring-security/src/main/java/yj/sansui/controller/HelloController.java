package yj.sansui.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yj.sansui.utils.VerifyCodeImage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author sansui
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return  "hello";
    }

    @GetMapping("/vercode")
    public void code(HttpServletRequest req, HttpServletResponse resp)throws IOException{
        VerifyCodeImage vc=new VerifyCodeImage();
        BufferedImage image= vc.getImage();
        String text= vc.getText();
        HttpSession session= req.getSession();
        session.setAttribute("index_code",text);
        VerifyCodeImage.output(image,resp.getOutputStream());
    }
}
