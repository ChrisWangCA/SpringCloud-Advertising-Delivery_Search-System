package pers.me.ad.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pers.me.ad.annotation.IgnoreResponseAdvice;
import pers.me.ad.client.SponsorClient;
import pers.me.ad.client.vo.AdPlan;
import pers.me.ad.client.vo.AdPlanGetRequest;
import pers.me.ad.vo.CommonResponse;

import java.util.List;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-13
 */
@Slf4j
@RestController
public class SearchController {

    private final RestTemplate restTemplate;

    private final SponsorClient sponsorClient;

    @Autowired
    public SearchController(RestTemplate restTemplate, SponsorClient sponsorClient) {
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
    }

    @IgnoreResponseAdvice
    @PostMapping("/getAdPlans")
    public CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request){
        log.info("ad-search: getAdPlans -> {}",
                JSON.toJSONString(request));
        return sponsorClient.getAdPlans(request);
    }

    @SuppressWarnings("all")
    @IgnoreResponseAdvice // 忽略统一响应
    @PostMapping("/getAdPlanByRibbon")
    public CommonResponse<List<AdPlan>> getAdPlansByRibbon(@RequestBody AdPlanGetRequest request) {
        log.info("ad-search: getAdPlansByRibbon -> {}",
                JSON.toJSONString(request));
        return restTemplate.postForEntity( //postForEntity方法会返回一个ResponseEntity对象，该对象封装了响应的所有信息，包括响应头、响应体、响应状态码等
                "http://eureka-client-ad-sponsor/ad-sponsor/get/adPlan",
                request,
                CommonResponse.class
        ).getBody();
    }

}
