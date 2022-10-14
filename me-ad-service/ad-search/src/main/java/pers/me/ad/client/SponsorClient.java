package pers.me.ad.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pers.me.ad.client.vo.AdPlan;
import pers.me.ad.client.vo.AdPlanGetRequest;
import pers.me.ad.vo.CommonResponse;

import java.util.List;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-13
 */


@FeignClient(value = "eureka-client-ad-sponsor",fallback = SponsorClientHystrix.class)  //指定了服务降级，当调用eureka-client-ad-sponsor服务失败时，会调用SponsorClientHystrix类中的方法
public interface SponsorClient {

    @RequestMapping(value = "/ad-sponsor/get/adPlan",method = RequestMethod.POST)
    CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request);
}
