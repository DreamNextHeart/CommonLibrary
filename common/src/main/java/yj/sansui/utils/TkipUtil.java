package yj.sansui.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Random;

/**
 * @author sansui
 */
public class TkipUtil {

    // 加密
    public static String degst(String str){
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] bb = md5.digest(str.getBytes(StandardCharsets.UTF_8));
            // 1:不需要正负号，32:输出三十二进制数据
            String r = new BigInteger(1, bb).toString(32);
            System.out.println("第一次加密"+r);
            return r;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //获取盐值
    public static String getSalt(){
        String salt="";
        String[] saltStr={"0","1","2","3","4","5","6","7","8","9",
                "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
                "A","B","C", "D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        for(int i=0;i<5;i++) {
            Random ru=new Random();
            String str=saltStr[ru.nextInt(61)];
//            System.out.println(str);
            salt+=str;
        }
        return salt;
    }

//    public static void test(){
//        Random ru=new Random();
//        for(int i=0;i<5;i++){
//            int temp=ru.nextInt(10);
//            System.out.println(temp);
//        }
//    }
}
