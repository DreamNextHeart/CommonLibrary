package yj.sansui.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 生成图片验证码
 * @author Sansui
 */
public class VerifyCodeImage {
    /**
     * 验证码图片的宽度
     */
    private int width=100;
    /**
     * 验证码图片的高度
     */
    private int height=50;
    /**
     * 定义字体
     */
    private String[] fontNames ={"宋体","楷体","隶书","微软雅黑"};
    /**
     * 定义验证码图片颜色
     */
    private Color bgColor=new Color(255,255,255);
    /**
     * 创建随机对象
     */
    private Random random=new Random();
    /**
     * 字符串可选范围
     */
    private String codes="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPGRSTUVWXYZ";
    /**
     * 随机文本
     */
    private String text;

    /**
     * randomColor 获取随机颜色
     * @return
     */
    private Color randomColor(){
        int red=random.nextInt(150);
        int green=random.nextInt(150);
        int blue=random.nextInt(150);
        return new Color(red,green,blue);
    }

    /**
     * randomFont 获取随机字体
     * @return Font对象
     */
    private Font randomFont(){
        String name = fontNames[random.nextInt(fontNames.length)];
        int style=random.nextInt(fontNames.length);
        int size=random.nextInt(5)+24;
        return new Font(name,style,size);
    }

    /**
     * randomChar 获取随机字符
     * @return char字符
     */
    private char randomChar(){
        return codes.charAt(random.nextInt(codes.length()));
    }

    /**
     * createImage 创建空白的BufferedImage对象
     * @return
     */
    private BufferedImage createImage(){
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2= (Graphics2D) image.getGraphics();
        g2.setColor(bgColor);
        g2.fillRect(0,0,width,height);
        return image;
    }

    public void drawLine(BufferedImage image){
        Graphics2D g2=(Graphics2D) image.getGraphics();
        int num=5;
        for(int i=0;i<num;i++){
            int x1=random.nextInt(width);
            int y1=random.nextInt(height);
            int x2=random.nextInt(width);
            int y2=random.nextInt(height);
            g2.setColor(randomColor());
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(x1,y1,x2,y2);
        }
    }

    public String getText(){
        return text;
    }

    public BufferedImage getImage(){
        BufferedImage image=createImage();
        Graphics2D g2= (Graphics2D) image.getGraphics();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<4;i++){
            String s=randomChar()+" ";
            sb.append(s);
            g2.setColor(randomColor());
            g2.setFont(randomFont());
            float x=i*width*1.0f/4;
            g2.drawString(s,x,height-15);
        }
        this.text=sb.toString();
        drawLine(image);
        return image;
    }

    public static void output(BufferedImage image, OutputStream out)throws IOException {
        ImageIO.write(image,"JPEG",out);
    }


}
