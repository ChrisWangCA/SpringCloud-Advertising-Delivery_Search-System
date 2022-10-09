package pers.me.ad.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.RequestContent;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-09
 */

@Slf4j
@Component
public class PreRequestFilter extends ZuulFilter {

    static final String START_TIME = "startTime";

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0; //数字越小，优先级越高
    }

    @Override
    public boolean shouldFilter() {
        return true;//是否执行该过滤器
    }

    //过滤器的具体逻辑
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set(START_TIME, System.currentTimeMillis());
        return null;
    }
}
