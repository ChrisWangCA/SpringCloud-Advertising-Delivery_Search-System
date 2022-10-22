package pers.me.ad.index.creative;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pers.me.ad.index.IndexAware;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-14
 */
@Slf4j
@Component
public class CreativeIndex implements IndexAware<Long,CreativeObject> {

    private static Map<Long,CreativeObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    public List<CreativeObject> fetch(Collection<Long> adIds){
        if(CollectionUtils.isEmpty(adIds)){
            return Collections.emptyList();
        }
        List<CreativeObject> result = new ArrayList<>();
        adIds.forEach(adId -> {
            CreativeObject object = get(adId);
            if(object == null){
                log.error("CreativeObject not found: {}",adId);
                return;
            }
            result.add(object);
        });
        return result;
    }


    @Override
    public CreativeObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, CreativeObject value) {
        log.info("before add: {}",objectMap);
        objectMap.put(key,value);
        log.info("after add: {}",objectMap);
    }

    @Override
    public void update(Long key, CreativeObject value) {
        log.info("before update:{}",objectMap);
        CreativeObject oldObject = objectMap.get(key);
        if (null == oldObject) {
            objectMap.put(key, value);
        } else{
            oldObject.update(value);
        }
        log.info("after update:{}",objectMap);
    }

    @Override
    public void delete(Long key, CreativeObject value) {
        log.info("before delete:{}",objectMap);
        objectMap.remove(key);
        log.info("after delete:{}",objectMap);
    }
}
