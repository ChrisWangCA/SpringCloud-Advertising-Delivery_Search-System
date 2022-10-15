package pers.me.ad.index.creative_unit;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.hql.internal.ast.tree.CollectionFunction;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pers.me.ad.index.IndexAware;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-14
 */
@Slf4j
@Component
public class CreativeUnitIndex implements IndexAware<String,CreativeUnitObject> {
    //key = adId-unitId value = CreativeUnitObject
    private static Map<String,CreativeUnitObject> objectMap;
    //key = adId value = unitId
    private static Map<Long, Set<Long>> creativeUnitMap;
    //<unitId, adId set>
    private static Map<Long, Set<Long>> unitCreativeMap;

    static {
        objectMap = new ConcurrentHashMap<>();
        creativeUnitMap = new ConcurrentHashMap<>();
        unitCreativeMap = new ConcurrentHashMap<>();
    }

    @Override
    public CreativeUnitObject get(String key) {
        return objectMap.get(key);
    }

    @Override
    public void add(String key, CreativeUnitObject value) {
        log.info("before add:{}",objectMap);
        objectMap.put(key,value);
        Set<Long> unitSet = creativeUnitMap.get(value.getAdId());
        if(CollectionUtils.isEmpty(unitSet)){
            unitSet = new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getAdId(),unitSet);
        }
        unitSet.add(value.getUnitId());
        Set<Long> creativeSet = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isEmpty(creativeSet)){
            creativeSet = new ConcurrentSkipListSet<>();
            unitCreativeMap.put(value.getUnitId(),creativeSet);
        }
        creativeSet.add(value.getAdId());
        log.info("after add: {}",objectMap);
    }

    @Override
    public void update(String key, CreativeUnitObject value) {
        log.error("CreativeUnitIndex not support update");
    }

    @Override
    public void delete(String key, CreativeUnitObject value) {
        log.info("before delete :{}",objectMap);
        objectMap.remove(key);
        Set<Long> unitSet = creativeUnitMap.get(value.getAdId());
        if (!CollectionUtils.isEmpty(unitSet)){
            unitSet.remove(value.getUnitId());
        }
        Set<Long> creativeSet = unitCreativeMap.get(value.getUnitId());
        if (!CollectionUtils.isEmpty(creativeSet)){
            creativeSet.remove(value.getAdId());
        }
        log.info("after delete:{}",objectMap);
    }
}
