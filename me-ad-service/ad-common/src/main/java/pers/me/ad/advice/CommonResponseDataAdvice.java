package pers.me.ad.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import pers.me.ad.annotation.IgnoreResponseAdvice;
import pers.me.ad.vo.CommonResponse;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-10
 */
@RestControllerAdvice //对响应进行统一拦截
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {
    @Override
    @SuppressWarnings("all") //忽略警告 (可能会有空指针)
    // 判断是否需要对响应进行处理
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果当前方法所在的类上标识了@IgnoreResponseAdvice注解，则不需要处理
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)){
            return false;
        }
        // 如果当前方法标识了@IgnoreResponseAdvice注解，则不需要处理
        if (methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)){
            return false;
        }

        return true;
    }

    @Nullable
    @Override
    @SuppressWarnings("all")
    //写入响应前的处理
    public Object beforeBodyWrite(@Nullable Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        CommonResponse<Object> response = new CommonResponse<>(0,"");//默认情况下，响应码为0，响应信息为空
        //object o是一个响应对象
        if(null == o){
            return response;
        }else if (o instanceof CommonResponse){ //如果响应对象是CommonResponse类型，则不需要处理
            response = (CommonResponse<Object>) o;
        }else{
            response.setData(o); //如果响应对象不是CommonResponse类型，则将响应对象作为CommonResponse的data属性
        }

        return null;
    }
}
