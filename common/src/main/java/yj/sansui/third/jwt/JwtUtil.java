package yj.sansui.third.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import yj.sansui.exception.CommonException;
import yj.sansui.exception.ExceptionCode;


import java.util.Calendar;
import java.util.Date;

/**
 * JwtUtil，JWT工具类
 *
 * @author sansui
 * @annotation Component 将JwtUtil注入Ioc容器管理
 */
@Component
public class JwtUtil {
    /**
     * secret 自定义密钥
     */
    private static String secret = "Sansui";

    /**
     * token有效时间
     */
    private static Integer jwtExpiresTime;

    @Value("${jwt.expires-time}")
    public void setJwtExpiresTime(Integer time) {
        jwtExpiresTime = time;
    }

    /**
     * createToken 生成token
     * 签发对象：账号
     * 签发时间：现在
     * 有效时间：配置文件可设置
     * 载荷内容：账号 密码
     * 加密密钥：这个人的id加上一串字符串
     *
     * @param passport String
     * @param password String
     * @return token
     */
    public static String createToken(String passport, String password) {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, jwtExpiresTime);
        Date expiresDate = nowTime.getTime();

        /*
          create 创建token
          withAudience 签发对象
          withIssuedAt 发行时间
          withExpiresAt 有效时间
          withClaim 载荷（不固定）
          sign 加密
         */
        return JWT.create().withAudience(passport)
                .withIssuedAt(new Date())
                .withExpiresAt(expiresDate)
                .withClaim("passport", passport)
                .withClaim("password", password)
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 检验合法性，其中secret参数就应该传入的是用户的id
     *
     * @param token String
     * @throws CommonException 通用异常类
     */
    public static Boolean verifyToken(String token) throws CommonException {
        boolean temp = false;
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            jwt = verifier.verify(token);
            return temp = true;
        } catch (Exception e) {
            //效验失败
            //这里抛出的异常是我自定义的一个异常，你也可以写成别的
            throw new CommonException(ExceptionCode.SIGNATURE_NOT_MATCH, "签名不匹配");
        }

    }

    /**
     * getAudience 获取签发对象
     * @param token String
     * @return audience签发对象
     * @throws CommonException 异常类
     */
    public static String getAudience(String token) throws CommonException {
        String audience = null;
        try {
            audience = JWT.decode(token).getAudience().get(0);
        } catch (Exception e) {
            //这里是token解析失败
            throw new CommonException(ExceptionCode.SIGNATURE_DECODE_EXCEPTION,"签名解析失败");
        }
        return audience;
    }


    /**
     * getClaimByName 通过载荷名字获取载荷的值
     * @param token String
     * @param name String
     * @return 载荷
     */
    public static Claim getClaimByName(String token, String name) {
        return JWT.decode(token).getClaim(name);
    }
}
