package pers.me.ad.index.keyword;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import pers.me.ad.index.IndexAware;
import pers.me.ad.utils.CommonUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-13
 */
@Slf4j
@Component
public class UnitKeywordIndex implements IndexAware<String, Set<Long>> {

    private static Map<String,Set<Long>> keywordUnitMap;
    private static Map<Long,Set<String>> unitKeywordMap;

    static{
        keywordUnitMap = new ConcurrentHashMap<>();
        unitKeywordMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        if (StringUtils.isEmpty(key)){
            return Collections.emptySet();
        }
        Set<Long> result = keywordUnitMap.get(key);
        if (result == null){
            return Collections.emptySet(); //emptySet()返回的是一个不可变的空集合
        }
        return result;
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitKeywordIndex, before add: {}",keywordUnitMap);
        Set<Long> unitIdSet = CommonUtils.getOrCreate(
                key,keywordUnitMap, ConcurrentSkipListSet::new //如果keywordUnitMap中不存在key，就会调用ConcurrentSkipListSet::new方法
        );
        unitIdSet.addAll(value);

        for (Long unitId : value) {
            Set<String> keywordSet = CommonUtils.getOrCreate(
                    unitId, unitKeywordMap,
                    ConcurrentSkipListSet::new
            );
            keywordSet.add(key);
        }
        log.info("UnitKeywordIndex, after add: {}",keywordUnitMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("keyword index cannot support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitKeywordIndex, before delete: {}",unitKeywordMap);
        Set<Long> unitIds = CommonUtils.getOrCreate(
                key,keywordUnitMap,ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);

        for (Long unitId:value){
            Set<String> keywordSet = CommonUtils.getOrCreate(
                    unitId,unitKeywordMap,ConcurrentSkipListSet::new
            );
            keywordSet.remove(key);
        }
        log.info("UnitKeywordIndex, after delete: {}",unitKeywordMap);
    }

    public boolean match(Long unitId, List<String> keywords){
        if (unitKeywordMap.containsKey(unitId) && CollectionUtils.isNotEmpty(unitKeywordMap.get(unitId))){
            Set<String> unitKeywords = unitKeywordMap.get(unitId);
            return CollectionUtils.isSubCollection(keywords,unitKeywords); //判断keywords是否是unitKeywords的子集，如果是，返回true
        }
        return false;
    }
}
