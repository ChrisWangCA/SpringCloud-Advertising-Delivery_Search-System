package pers.me.ad.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.me.ad.exception.AdException;
import pers.me.ad.vo.CommonResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-10
 */

@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(value = AdException.class) //捕获AdException异常
    public CommonResponse<String> handlerAdException(HttpServletRequest req, AdException ex){
        CommonResponse<String> response = new CommonResponse<>(-1,"Business Error");
        response.setData(ex.getMessage());
        return response;
    }
}
