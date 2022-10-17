package pers.me.ad.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import pers.me.ad.dump.table.*;
import pers.me.ad.index.DataTable;
import pers.me.ad.index.IndexAware;
import pers.me.ad.index.ad_plan.AdPlanIndex;
import pers.me.ad.index.ad_plan.AdPlanObject;
import pers.me.ad.index.ad_unit.AdUnitIndex;
import pers.me.ad.index.ad_unit.AdUnitObject;
import pers.me.ad.index.creative.CreativeIndex;
import pers.me.ad.index.creative.CreativeObject;
import pers.me.ad.index.creative_unit.CreativeUnitIndex;
import pers.me.ad.index.creative_unit.CreativeUnitObject;
import pers.me.ad.index.district.UnitDistrictIndex;
import pers.me.ad.index.interest.UnitItIndex;
import pers.me.ad.index.keyword.UnitKeywordIndex;
import pers.me.ad.mysql.constant.OpType;
import pers.me.ad.utils.CommonUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-16
 */
@Slf4j
//索引之间存在层级关系，也就是说，如果想要获取某个推广单元的推广计划，必须先获取推广单元，然后再获取推广计划
public class AdLevelDataHandler {

    public static void handlerLevel1(AdPlanTable planTable, OpType type) {
        AdPlanObject planObject = new AdPlanObject(
                planTable.getId(),
                planTable.getUserId(),
                planTable.getPlanStatus(),
                planTable.getStartDate(),
                planTable.getEndDate()
        );
        handlerBinLogEvent(DataTable.of(AdPlanIndex.class), planObject.getPlanId(), planObject, type);
    }

    public static void handlerLevel2(AdCreativeTable creativeTable, OpType type) {
        CreativeObject creativeObject = new CreativeObject(
                creativeTable.getAdId(),
                creativeTable.getName(),
                creativeTable.getType(),
                creativeTable.getMaterialType(),
                creativeTable.getHeight(),
                creativeTable.getWidth(),
                creativeTable.getAuditStatus(),
                creativeTable.getAdUrl()
        );

        handlerBinLogEvent(DataTable.of(CreativeIndex.class), creativeObject.getAdId(), creativeObject, type);
    }

    public static void handlerLevel3(AdUnitTable unitTable, OpType type) {
        AdPlanObject adPlanObject = DataTable.of(AdPlanIndex.class).get(unitTable.getPlanId());
        if (null == adPlanObject) {
            log.error("handlerLevel3 found AdPlanObject error: {}", unitTable.getPlanId());
            return;
        }
        AdUnitObject unitObject = new AdUnitObject(unitTable.getUnitId(), unitTable.getUnitStatus(), unitTable.getPositionType(), unitTable.getPlanId(), adPlanObject);
        handlerBinLogEvent(DataTable.of(AdUnitIndex.class), unitTable.getUnitId(), unitObject, type);
    }


    public static void handlerLevel3(AdCreativeUnitTable creativeUnitTable, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("CreativeUnitIndex not support update");
            return;
        }
        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(creativeUnitTable.getUnitId());
        CreativeObject creativeObject = DataTable.of(CreativeIndex.class).get(creativeUnitTable.getAdId());
        if (null == unitObject || null == creativeObject) {
            log.error("handlerLevel3 found AdUnitObject or CreativeObject error: {}", JSON.toJSONString(creativeUnitTable));
            return;
        }
        CreativeUnitObject creativeUnitObject = new CreativeUnitObject(creativeUnitTable.getAdId(), creativeUnitTable.getUnitId());
        handlerBinLogEvent(
                DataTable.of(CreativeUnitIndex.class),
                CommonUtils.stringConcat(creativeUnitObject.getAdId().toString(), creativeUnitObject.getUnitId().toString()),
                creativeUnitObject, type
        );
    }


    public static void handlerLevel4(AdUnitDistrictTable unitDistrictTable, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("district index can not support update");
            return;
        }
        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(unitDistrictTable.getUnitId());
        if (null == unitObject) {
            log.error("handlerLevel4 found unitDistrictTable error: {}", unitDistrictTable.getUnitId());
            return;
        }
        String key = CommonUtils.stringConcat(unitDistrictTable.getProvince(), unitDistrictTable.getCity());
        Set<Long> value = new HashSet<>(Collections.singleton(unitDistrictTable.getUnitId()));
        handlerBinLogEvent(DataTable.of(UnitDistrictIndex.class), key, value, type);
    }

    public static void handlerLevel4(AdUnitItTable unitItTable, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("it index can not support update");
            return;
        }
        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(unitItTable.getUnitId());
        if (null == unitObject) {
            log.error("handlerLevel4 found AdUnitItTable error: {}", unitItTable.getUnitId());
            return;
        }
        Set<Long> value = new HashSet<>(Collections.singleton(unitItTable.getUnitId()));
        handlerBinLogEvent(DataTable.of(UnitItIndex.class), unitItTable.getItTag(), value, type);
    }

    public static void handlerLevel4(AdUnitKeywordTable keywordTable,OpType type){
        if (type == OpType.UPDATE) {
            log.error("keyword index can not support update");
            return;
        }
        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(keywordTable.getUnitId());
        if (null == unitObject) {
            log.error("handlerLevel4 found AdUnitKeywordTable error: {}", keywordTable.getUnitId());
            return;
        }
        Set<Long> value = new HashSet<>(Collections.singleton(keywordTable.getUnitId()));
        handlerBinLogEvent(DataTable.of(UnitKeywordIndex.class), keywordTable.getKeyword(), value, type);
    }


    private static <K, V> void handlerBinLogEvent(IndexAware<K, V> index, K key, V value, OpType type) {
        switch (type) {
            case ADD:
                index.add(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;


        }
    }

}
