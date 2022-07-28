package yj.sansui.exception;


import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import yj.sansui.result.Result;
import yj.sansui.result.ResultCode;


/**
 * ExceptionInterceptor，异常拦截器
 * 拦截异常后封装成Result返回结果
 *
 * @author Sansui
 * @annotation RestControllerAdvice 默认全局拦截器
 */
@RestControllerAdvice
public class ExceptionInterceptor {

    /**
     * methodArgumentNotValidExceptionHandler，方法参数无效异常处理器（可重载，参数为异常类）
     *
     * @param e BindException
     * @return Result
     * @annotation ExceptionHandler 异常处理器
     */
    @ExceptionHandler({BindException.class})
    public Result methodArgumentNotValidExceptionHandler(BindException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return new Result(ResultCode.VALIDATE_ERROR, objectError.getDefaultMessage());
    }

    /**
     * commonExceptionHandler，通用异常处理器
     *
     * @param e CommonException
     * @return Result
     */
    @ExceptionHandler(CommonException.class)
    public Result commonExceptionHandler(CommonException e) {
        return new Result(e.getCode(), e.getMessage());
    }
}
