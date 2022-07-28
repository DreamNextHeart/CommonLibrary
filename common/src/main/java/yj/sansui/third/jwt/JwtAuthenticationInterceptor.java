package yj.sansui.third.jwt;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import yj.sansui.exception.CommonException;
import yj.sansui.exception.ExceptionCode;
import yj.sansui.third.jwt.annotation.PassVerify;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * JwtAuthenticationInterceptor，JWT框架的拦截器，校验token
 * @author sansui
 */
public class JwtAuthenticationInterceptor implements HandlerInterceptor {
    /**
     * 重写preHandle方法，对请求进行拦截并从请求头中Authorization取出token进行校验
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param object Object
     * @return ture/false
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 从请求头中取出 token  这里需要和前端约定好把jwt放到请求头一个叫token的地方
        String token = httpServletRequest.getHeader("Authorization");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassVerify.class)) {
            PassVerify passVerify = method.getAnnotation(PassVerify.class);
            if (passVerify.required()) {
                return true;
            }
        }
        //默认全部检查
        else {
            // 执行认证
            if (token == null) {
                // 没传token，不通过
                throw new CommonException(ExceptionCode.SIGNATURE_IS_NULL,"签名为空");
            }
            String[] tokenSplit = token.split(" ");
            if (!Objects.equals("Bearer", tokenSplit[0])) {
                throw new CommonException(ExceptionCode.SIGNATURE_NOT_MATCH,"签名不匹配");
            }

            // 验证 token
            JwtUtil.verifyToken(tokenSplit[1]);

            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
