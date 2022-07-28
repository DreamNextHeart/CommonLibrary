package yj.sansui.pack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import yj.sansui.exception.CommonException;
import yj.sansui.result.Result;
import yj.sansui.result.ResultCode;


/**
 * ResponsePackInterceptor 响应结果拦截器
 * 拦截响应结果后统一封装成Result返回
 * 流程如下：
 * 1.当获取请求进行返回值前，该拦截器将返回值拦截进行判断
 * 2.supports方法判断返回值是否为Result（已统一封装）或拥有自定义注解@NotControllerResponse，是则直接返回，否便进行下一步
 * 3.beforeBodyWrite方法将原本的返回值封装进Result返回，若返回值为String类型，需要将String类型转换为json串再封装进Result返回
 * @author Sansui
 * @annotation RestControllerAdvice 默认全局拦截器，可自定义针对特定包进行拦截
 */
@RestControllerAdvice(basePackages = {"yj.sansui"})
public class ResponsePackInterceptor implements ResponseBodyAdvice<Object> {
    /**
     * 实现supports方法
     * 判断返回值是否为Result（已统一封装）或拥有自定义注解@NotControllerResponse
     * @param methodParameter MethodParameter
     * @param aClass Class
     * @return true/false
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // response是Result类型，或者注释了NotControllerResponse都不进行封装
        return !(methodParameter.getParameterType().isAssignableFrom(Result.class)
                || methodParameter.hasMethodAnnotation(NotControllerResponse.class));
    }

    /**
     * 实现beforeBodyWrite方法
     * 将原本的返回值封装进Result返回，若返回值为String类型，需要将String类型转换为json串再封装进Result返回
     * 针对该方法的参数做备注，方法参数最多为3个，超出三个封装成对象作为方法参数，这里是对应源码方法
     * @param data Object
     * @param returnType MethodParameter
     * @param mediaType MediaType
     * @param aClass Class
     * @param request ServerHttpRequest
     * @param response ServerHttpResponse
     * @return new Result(data)
     */
    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse response) {
        // String类型不能直接封装
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据封装在Result里后转换为json串进行返回
                return objectMapper.writeValueAsString(new Result(data));
            } catch (JsonProcessingException e) {
                throw new CommonException( ResultCode.RESPONSE_PACK_ERROR, e.getMessage());
            }
        }
        // 否则直接封装成Result返回
        return new Result(data);
    }
}
