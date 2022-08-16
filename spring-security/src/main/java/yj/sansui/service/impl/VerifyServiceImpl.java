package yj.sansui.service.impl;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import yj.sansui.RedisUtil;
import yj.sansui.result.Result;
import yj.sansui.service.VerifyService;
import yj.sansui.utils.VerifyUtil;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;

/**
 * VerifyServiceImpl 业务实现类
 * @author zpli
 */
@Service
public class VerifyServiceImpl implements VerifyService {
    @Override
    public void createCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        //获取session
        HttpSession session = request.getSession();
        //获得sessionId
        String id = session.getId();
        System.out.println();
        ResponseCookie cookie = ResponseCookie.from("JSESSIONID",id)
                .secure(true)
                .domain("")
                .path("/")
                .maxAge(Duration.ofHours(1))
                .sameSite("None")
                .build();

        //清除之前缓存的图片验证码
        if (!String.valueOf(request.getSession().getAttribute("SESSION_VERIFY_CODE_"+id)).isEmpty()){
            String getVerify = String.valueOf(request.getSession().getAttribute("SESSION_VERIFY_CODE_"+id));
            RedisUtil.del(getVerify);
            System.out.println("清除成功");
        }

        //生成图片验证码,用的默认参数
        Object[] verify = VerifyUtil.newBuilder().build().createImage();

        //将验证码存入session
        session.setAttribute("SESSION_VERIFY_CODE_" + id, verify[0]);
        //打印验证码
        System.out.println(verify[0]);
        //将验证码存入redis
        RedisUtil.setKeyValueTime((String) verify[0],id,5*60);

        //将图片传给浏览器
        BufferedImage image = (BufferedImage) verify[1];
        response.setContentType("image/png");
        response.setHeader(HttpHeaders.SET_COOKIE,cookie.toString());
        OutputStream ops = response.getOutputStream();
        ImageIO.write(image,"png",ops);
    }

    @Override
    public Result checkCode(String verificationCode) {
        if (!RedisUtil.hasKey(verificationCode)){
            return new Result("验证码错误");
        }
        RedisUtil.del(verificationCode);
        return new Result(200);

    }
}
