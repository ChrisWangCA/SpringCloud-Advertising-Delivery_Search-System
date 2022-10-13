package pers.me.ad.serivce;

import pers.me.ad.entity.AdPlan;
import pers.me.ad.exception.AdException;
import pers.me.ad.vo.AdPlanGetRequest;
import pers.me.ad.vo.AdPlanRequest;
import pers.me.ad.vo.AdPlanResponse;

import java.util.List;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-12
 */
public interface IAdPlanService {
    // 创建推广计划
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;
    //批量获取推广计划
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;
    // 更新推广计划
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;
    // 删除推广计划
    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
