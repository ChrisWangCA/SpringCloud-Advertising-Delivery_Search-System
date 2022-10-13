package pers.me.ad.serivce.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.me.ad.constant.CommonStatus;
import pers.me.ad.constant.Constants;
import pers.me.ad.dao.AdPlanRepository;
import pers.me.ad.dao.AdUserRepository;
import pers.me.ad.entity.AdPlan;
import pers.me.ad.entity.AdUser;
import pers.me.ad.exception.AdException;
import pers.me.ad.serivce.IAdPlanService;
import pers.me.ad.utils.CommonUntils;
import pers.me.ad.vo.AdPlanGetRequest;
import pers.me.ad.vo.AdPlanRequest;
import pers.me.ad.vo.AdPlanResponse;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-12
 */
@Service
public class AdPlanServiceImpl implements IAdPlanService {

    private final AdUserRepository userRepository;
    private final AdPlanRepository planRepository;
    @Autowired
    public AdPlanServiceImpl(AdUserRepository userRepository, AdPlanRepository planRepository) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }


    @Override
    @Transactional
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {
        if (!request.createValidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        //确保关联的User存在
        Optional<AdUser> adUser = userRepository.findById(request.getUserId());
        if(!adUser.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CANNOT_FIND_RECORD);
        }
        AdPlan oldPlan = planRepository.findByUserIdAndPlanName(request.getUserId(), request.getPlanName());
        if (oldPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }

        //经过上面的检查，可以创建新的AdPlan
        AdPlan newAdPlan = planRepository.save(new AdPlan(request.getUserId(),request.getPlanName(),
                CommonUntils.parseStringDate(request.getStartDate()),CommonUntils.parseStringDate(request.getEndDate())));
        return new AdPlanResponse(newAdPlan.getId(),newAdPlan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        return planRepository.findAllByIdInAndUserId(request.getIds(),request.getUserId());

    }

    @Override
    @Transactional
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {
        if(!request.updateValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan plan = planRepository.findByIdAndUserId(request.getId(),request.getUserId());
        if(plan == null){
            throw new AdException(Constants.ErrorMsg.CANNOT_FIND_RECORD);
        }
        if(request.getPlanName() != null) {
            plan.setPlanName(request.getPlanName());
        }
        if (request.getStartDate() != null) {
            plan.setStartDate(CommonUntils.parseStringDate(request.getStartDate()));
        }
        if (request.getEndDate() != null) {
            plan.setEndDate(CommonUntils.parseStringDate(request.getEndDate()));
        }
        plan.setUpdateTime(new Date());
        plan = planRepository.save(plan);
        return new AdPlanResponse(plan.getId(),plan.getPlanName());
    }

    @Override
    @Transactional
    public void deleteAdPlan(AdPlanRequest request) throws AdException {
        if (!request.deleteValidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan plan = planRepository.findByIdAndUserId(request.getId(),request.getUserId());
        if (plan == null){
            throw new AdException(Constants.ErrorMsg.CANNOT_FIND_RECORD);
        }
        //将plan的状态改为无效
        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        plan.setUpdateTime(new Date());
        planRepository.save(plan);
    }

}
