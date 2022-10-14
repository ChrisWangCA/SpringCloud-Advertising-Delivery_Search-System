package pers.me.ad.index.ad_unit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.me.ad.index.ad_plan.AdPlanObject;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitObject {

    private Long unitId;

    private Integer unitStatus;

    private Integer positionType;

    private Long planId;

    private AdPlanObject adPlanObject;

    void update(AdUnitObject newObject){
        if (null != newObject.getUnitId()){
            this.unitId = newObject.getUnitId();
        }
        if(null != newObject.getUnitStatus()){
            this.unitStatus = newObject.getUnitStatus();
        }
        if(null != newObject.getPositionType()){
            this.positionType = newObject.getPositionType();
        }
        if(null != newObject.getPlanId()){
            this.planId = newObject.getPlanId();
        }
        if(null != newObject.getAdPlanObject()){
            this.adPlanObject = newObject.getAdPlanObject();
        }
    }

}
