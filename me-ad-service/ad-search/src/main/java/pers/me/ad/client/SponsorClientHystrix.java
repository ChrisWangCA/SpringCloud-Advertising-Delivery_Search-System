package pers.me.ad.client;

import org.springframework.stereotype.Component;
import pers.me.ad.client.vo.AdPlan;
import pers.me.ad.client.vo.AdPlanGetRequest;
import pers.me.ad.vo.CommonResponse;

import java.util.List;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-13
 */
@Component
public class SponsorClientHystrix implements SponsorClient{
    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
        return new CommonResponse<>(-1,"eureka-client-ad-sponsor error");
    }
}
