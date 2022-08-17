package yj.sansui.utils;



import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import yj.sansui.RedisUtil;
import yj.sansui.result.Result;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.util.Random;


/**
 * VerifyUtil 生成图形验证码的工具类
 *
 * @author Sansui
 */
public class VerifyCode {
    /**
     * codes,验证码字符集
     * size,验证码字符数量
     * lines,干扰线数量
     * width,验证码图片宽度
     * height,验证码图片高度
     * tilt,字符倾斜程度
     * backgroundColor,验证码图片背景颜色
     */
    private static final char[] CODES = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    private Integer size;
    private Integer lines;
    private Integer width;
    private Integer height;
    private Integer fontSize;
    private boolean tilt;
    private Color backgroundColor;

    /**
     * Builder 构造器对象
     */
    @Data
    public static class Builder {
        /**
         * size,验证码字符数量
         * lines,干扰线数量
         * width,图片宽度
         * height,图片高度
         * fontSize,字体大小
         * tilt,是否倾斜
         * backgroundColor,背景颜色
         */
        private Integer size = 4;
        private Integer lines = 10;
        private Integer width = 80;
        private Integer height = 35;
        private Integer fontSize = 25;
        private boolean tilt = true;
        private Color backgroundColor = Color.LIGHT_GRAY;
        public VerifyCode build(){
            return new VerifyCode(this);
        }
    }

    /**
     * 实例化构造器对象
     * @return new Builder()
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * 初始化基础参数
     * @param builder Builder
     */
    private VerifyCode(Builder builder) {
        size=builder.size;
        lines=builder.lines;
        width=builder.width;
        height= builder.height;
        fontSize=builder.fontSize;
        tilt=builder.tilt;
        backgroundColor=builder.backgroundColor;
    }

    /**
     * getRandomColor,获取随机颜色
     * @return color
     */
    public Color getRandomColor(){
        Random ran=new Random();
        Color color=new Color(ran.nextInt(25),ran.nextInt(255),ran.nextInt(255));
        return color;
    }

    public Object[] createImage(){
        StringBuffer sb=new StringBuffer();
        //创建空白图片
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //获取图片画笔
        Graphics2D graphics=image.createGraphics();
        //设置抗锯齿
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //设置画笔颜色
        graphics.setColor(backgroundColor);
        //绘制矩形背景
        graphics.fillRect(0,0,width,height);
        //获取随机字符
        Random ran=new Random();
        //计算每个字符占的宽度，预留一个字符宽度用于左右边距
        int codeWidth=width/(size+1);
        //字符y轴的坐标
        int y=height*3/4;
        for(int i=0;i<size;i++){
            //设置随机颜色
            graphics.setColor(getRandomColor());
            //初始化字体
            Font font=new Font(null,Font.BOLD+Font.ITALIC,fontSize);
            if(tilt){
                //随机倾斜一个角度
                int theta=ran.nextInt(45);
                //随机倾斜方向，左/右
                theta=(ran.nextBoolean()==true)?theta:-theta;
                //AffineTransform,二维仿射变换的功能,它是一种二维坐标到二维坐标之间的线性变换，保持二维图形的“平直性”和“平行性”
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.rotate(Math.toRadians(theta), 0, 0);
                font = font.deriveFont(affineTransform);
            }
            //设置字体大小
            graphics.setFont(font);
            //计算当前字符绘制的x轴坐标
            int x=(i*codeWidth)+(codeWidth/2);
            //获取随机字符索引
            int n=ran.nextInt(CODES.length);
            //得到字符文本
            String code=String.valueOf(CODES[n]);
            //画字符
            graphics.drawString(code,x,y);
            //记录字符
            sb.append(code);
        }
        for(int i=0;i<lines;i++){
            //设置随机颜色
            graphics.setColor(getRandomColor());
            //随机画线
            graphics.drawLine(ran.nextInt(width), ran.nextInt(height), ran.nextInt(width), ran.nextInt(height));
        }
        //返回验证码和图片
        return new Object[]{sb.toString(),image};
    }
    public static void createCode(HttpServletResponse response, HttpServletRequest request)throws IOException{
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
        Object[] verify = VerifyCode.newBuilder().build().createImage();

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

    public static Result checkCode(String verifyCode){
        if (!RedisUtil.hasKey(verifyCode)){
            return new Result("验证码错误");
        }
        RedisUtil.del(verifyCode);
        return new Result(200);
    }
}

